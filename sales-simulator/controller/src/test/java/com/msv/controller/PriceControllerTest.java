package com.msv.controller;

import com.msv.application.port.PriceService;
import com.msv.application.entity.PriceRequest;
import com.msv.controller.entity.PriceResponse;
import com.msv.controller.mapper.PriceControllerMapper;
import com.msv.domain.entity.Price;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    @Mock
    private PriceService priceService;

    @Mock
    private PriceControllerMapper mapper;

    @InjectMocks
    private PriceController salesController;

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
        Mockito.doReturn(price).when(priceService).getPrice(request);

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

        verify(priceService).getPrice(request);
        verify(priceService, times(1)).getPrice(any());
        verify(mapper).toResponse(price);
        verify(mapper, times(1)).toResponse(any());
    }

    @ParameterizedTest
    @MethodSource("inputCases")
    void getSalesInputError(LocalDateTime applicationDate, String productId, String chainId, ResponseEntity<PriceResponse> expected) throws Exception {

        assertEquals(expected, salesController.getSales(applicationDate, productId, chainId));

        verifyNoInteractions(priceService);
        verifyNoInteractions(mapper);
    }

    @Test
    void getSalesNotFound() throws Exception {

        PriceRequest request = PriceRequest.builder()
                .chainId("1")
                .productId("35455")
                .applicationDate(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0)).build();
        Mockito.doThrow(new Exception()).when(priceService).getPrice(request);
        ResponseEntity result = salesController.getSales(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0), "35455", "1");
        ResponseEntity<PriceResponse> expected = ResponseEntity.notFound().build();
        assertEquals(expected, result);

        verify(priceService).getPrice(request);
        verify(priceService, times(1)).getPrice(any());
        verifyNoInteractions(mapper);
    }
}