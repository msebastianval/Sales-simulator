package com.msv.controller;

import com.msv.application.entity.PriceRequest;
import com.msv.application.exception.PriceNotFoundException;
import com.msv.application.port.PriceService;
import com.msv.controller.mapper.PriceControllerMapper;
import com.msv.controller.model.PriceResponse;
import com.msv.domain.entity.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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


    @Test
    void getSalesCorrect() {
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

        PriceResponse response = new PriceResponse()
                .brandId("1")
                .productId("35455")
                .price(10.0)
                .currency("EUR")
                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0).atOffset(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59).atOffset(ZoneOffset.UTC));

        Mockito.doReturn(response).when(mapper).toResponse(price);
        ResponseEntity<PriceResponse> result = salesController.getSales(OffsetDateTime.of(2020, 6, 14, 10, 0, 0, 0, ZoneOffset.UTC), "35455", "1");
        ResponseEntity<PriceResponse> expected = ResponseEntity.ok(response);
        assertEquals(expected, result);

        verify(priceService).getPrice(request);
        verify(priceService, times(1)).getPrice(any());
        verify(mapper).toResponse(price);
        verify(mapper, times(1)).toResponse(any());
    }

    @Test
    void getSalesNotFound() {

        PriceRequest request = PriceRequest.builder()
                .chainId("1")
                .productId("35455")
                .applicationDate(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0)).build();
        Mockito.doThrow(new PriceNotFoundException("Price not found")).when(priceService).getPrice(request);
        
        org.junit.jupiter.api.Assertions.assertThrowsExactly(PriceNotFoundException.class, () -> 
                salesController.getSales(OffsetDateTime.of(2020, 6, 14, 10, 0, 0, 0, ZoneOffset.UTC), "35455", "1")
        );

        verify(priceService).getPrice(request);
        verify(priceService, times(1)).getPrice(any());
        verifyNoInteractions(mapper);
    }
}