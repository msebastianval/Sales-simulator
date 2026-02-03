package com.msv.boot;

import com.msv.controller.model.PriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BootApplication.class)
@AutoConfigureMockMvc
class SalesSimulatorITTest {

    @Autowired
    private MockMvc mockMvc;

    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of(
                        LocalDateTime.of(2020, Month.JUNE, 14, 10, 0),
                        "35455",
                        "1",
                        new PriceResponse()
                                .brandId("1")
                                .productId("35455")
                                .currency("EUR")
                                .price(35.50)
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0, 0).atOffset(ZoneOffset.UTC))
                                .endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59).atOffset(ZoneOffset.UTC))

                ),
                Arguments.of(
                        LocalDateTime.of(2020, Month.JUNE, 14, 16, 0, 0),
                        "35455",
                        "1",
                        new PriceResponse()
                                .brandId("1")
                                .productId("35455")
                                .currency("EUR")
                                .price(25.45)
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 15, 0, 0).atOffset(ZoneOffset.UTC))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 18, 30, 0).atOffset(ZoneOffset.UTC))

                ),
                Arguments.of(
                        LocalDateTime.of(2020, Month.JUNE, 14, 21, 0),
                        "35455",
                        "1",
                        new PriceResponse()
                                .brandId("1")
                                .productId("35455")
                                .currency("EUR")
                                .price(35.50)
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0,0).atOffset(ZoneOffset.UTC))
                                .endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59,59).atOffset(ZoneOffset.UTC))

                ),
                Arguments.of(
                        LocalDateTime.of(2020, Month.JUNE, 15, 10, 0),
                        "35455",
                        "1",
                        new PriceResponse()
                                .brandId("1")
                                .productId("35455")
                                .currency("EUR")
                                .price(30.5)
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 15, 0, 0).atOffset(ZoneOffset.UTC))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 15, 11, 0).atOffset(ZoneOffset.UTC))

                ),
                Arguments.of(
                        LocalDateTime.of(2020, Month.JUNE, 16, 21, 0, 0),
                        "35455",
                        "1",
                        new PriceResponse()
                                .brandId("1")
                                .productId("35455")
                                .currency("EUR")
                                .price(38.95)
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 15, 16, 0, 0).atOffset(ZoneOffset.UTC))
                                .endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59).atOffset(ZoneOffset.UTC))

                )

        );


    }

    private static Stream<Arguments> invalidTestCases() {
        return Stream.of(
                Arguments.of(
                        "/v1/sales?applicationDate=&productId=35455&chainId=1",
                        "Missing parameter: applicationDate"),
                Arguments.of(
                        "/v1/sales?applicationDate=2020-06-14T10:00:00Z&chainId=1",
                        "Missing parameter: productId"),
                Arguments.of(
                        "/v1/sales?applicationDate=2020-06-14T10:00:00Z&productId=35455",
                        "Missing parameter: chainId"),
                Arguments.of(
                        "/v1/sales",
                        "Missing parameter: applicationDate")
        );
    }
    @ParameterizedTest
    @MethodSource("testCases")
    void salesTest(LocalDateTime applicationDate, String productId, String chainId, PriceResponse expected) throws Exception {
        String formattedDate = applicationDate.atOffset(ZoneOffset.UTC).format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        mockMvc.perform(get("/v1/sales?applicationDate=" + formattedDate + "&productId=" + productId + "&chainId=" + chainId).accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(expected.getBrandId()))
                .andExpect(jsonPath("$.price").value(expected.getPrice()))
                .andExpect(jsonPath("$.productId").value(expected.getProductId()))
                .andExpect(jsonPath("$.startDate").value(expected.getStartDate().toInstant().toString()))
                .andExpect(jsonPath("$.endDate").value(expected.getEndDate().toInstant().toString()))
                .andExpect(jsonPath("$.currency").value(expected.getCurrency()));
    }

    @ParameterizedTest
    @MethodSource("invalidTestCases")
    void whenParameterMissing_thenReturnsBadRequest(String request, String expectedMessage) throws Exception {
        mockMvc.perform(get(request).accept("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedMessage));

    }

    @Test
    void whenPriceNotFound_thenReturnsNotFound() throws Exception {
        mockMvc.perform(get("/v1/sales?applicationDate=2025-06-14T10:00:00Z&productId=99999&chainId=1").accept("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Price not found for the given parameters"));
    }

}
