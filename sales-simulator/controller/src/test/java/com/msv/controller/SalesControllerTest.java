package com.msv.controller;

import com.msv.application.SalesService;
import com.msv.application.entity.PriceRequest;
import com.msv.controller.entity.PriceResponse;
import com.msv.controller.mapper.PriceControllerMapper;
import com.msv.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SalesControllerTest {

    @Mock
    private SalesService salesService;

    @Mock
    private PriceControllerMapper mapper;

    @InjectMocks
    private SalesController salesController;

    @BeforeEach
    public void setUp() {
    }
    private static Stream<Arguments> inputCases() {
        return Stream.of(
                Arguments.of(
                        null,
                        "35455",
                        "1",
                        ResponseEntity.badRequest().build()
                ),
                Arguments.of(
                        LocalDateTime.of(2020, Month.JUNE, 14, 10, 0),
                        null,
                        "1",
                        ResponseEntity.badRequest().build()
                ),
                Arguments.of(
                        LocalDateTime.of(2020, Month.JUNE, 14, 10, 0),
                        "35455",
                        null,
                        ResponseEntity.badRequest().build()
                )

        );
    }

    @Test
    void getSalesCorrect() throws Exception {
        Price price = Price.builder()
                .brandId("1")
                .productId("35455")
                .price(10.0)
                .currency("EUR")
                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                .build();

        PriceRequest request = PriceRequest.builder()
                .chainId("1")
                .productId("35455")
                .applicationDate(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0)).build();
        Mockito.doReturn(price).when(salesService).getPrice(request);

        Mockito.doReturn(PriceResponse.builder()
                .brandId("1")
                .productId("35455")
                .price(10.0)
                .currency("EUR")
                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                .build()).when(mapper).toResponse(price);
        ResponseEntity<PriceResponse> result = salesController.getSales(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0), "35455", "1");
        ResponseEntity<PriceResponse> expected = ResponseEntity.ok(PriceResponse.builder()
                .brandId("1")
                .productId("35455")
                .price(10.0)
                .currency("EUR")
                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                .build());
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("inputCases")
    void getSalesInputError(LocalDateTime applicationDate, String productId, String chainId, ResponseEntity<PriceResponse> expected) throws Exception {

        assertEquals(expected, salesController.getSales(applicationDate, productId, chainId));
    }

    @Test
    void getSalesNotFound() throws Exception {

        PriceRequest request = PriceRequest.builder()
                .chainId("1")
                .productId("35455")
                .applicationDate(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0)).build();
        Mockito.doThrow(new Exception()).when(salesService).getPrice(request);
        ResponseEntity result = salesController.getSales(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0), "35455", "1");
        ResponseEntity<PriceResponse> expected = ResponseEntity.notFound().build();
        assertEquals(expected, result);
    }
}