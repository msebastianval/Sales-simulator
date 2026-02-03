package com.msv.application.adapter;

import com.msv.application.entity.PriceRequest;
import com.msv.application.exception.PriceNotFoundException;
import com.msv.application.mapper.PriceApplicationMapper;
import com.msv.domain.entity.Price;
import com.msv.domain.entity.PriceCmd;
import com.msv.domain.repository.PriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceApplicationMapper mapper;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Test
    void getPrice() {
        PriceRequest request = PriceRequest.builder()
                .productId("1234")
                .chainId("1")
                .applicationDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .build();
        PriceCmd priceCmd = PriceCmd.builder()
                .productId("1234")
                .chainId("1")
                .applicationDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .build();
        Price price = Price.builder()
                .startDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .endDate(LocalDateTime.of(2020, Month.JANUARY, 31, 23, 59))
                .productId("1234")
                .brandId("1")
                .price(12.34)
                .currency("EUR")
                .build();
        Price expected = Price.builder()
                .startDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .endDate(LocalDateTime.of(2020, Month.JANUARY, 31, 23, 59))
                .productId("1234")
                .brandId("1")
                .price(12.34)
                .currency("EUR")
                .build();
        Mockito.doReturn(priceCmd).when(mapper).toCmd(request);
        Mockito.doReturn(List.of(price)).when(priceRepository).getPrice(priceCmd);

        Assertions.assertEquals(expected, priceService.getPrice(request));

        verify(mapper).toCmd(request);
        verify(mapper, times(1)).toCmd(any());
        verify(priceRepository).getPrice(priceCmd);
        verify(priceRepository, times(1)).getPrice(any());
    }

    @Test
    void getPriceNull() {
        PriceRequest request = PriceRequest.builder()
                .productId("1234")
                .chainId("1")
                .applicationDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .build();
        PriceCmd priceCmd = PriceCmd.builder()
                .productId("1234")
                .chainId("1")
                .applicationDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .build();
        Mockito.doReturn(priceCmd).when(mapper).toCmd(request);
        Mockito.doReturn(Collections.emptyList()).when(priceRepository).getPrice(priceCmd);

        Assertions.assertThrowsExactly(PriceNotFoundException.class, () -> priceService.getPrice(request));

        verify(mapper).toCmd(request);
        verify(mapper, times(1)).toCmd(any());
        verify(priceRepository).getPrice(priceCmd);
        verify(priceRepository, times(1)).getPrice(any());
    }

    @Test
    void getPriceWithPriority() {
        PriceRequest request = PriceRequest.builder()
                .productId("1234")
                .chainId("1")
                .applicationDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .build();
        PriceCmd priceCmd = PriceCmd.builder()
                .productId("1234")
                .chainId("1")
                .applicationDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
                .build();
        Price price1 = Price.builder().priority(0).price(10.0).build();
        Price price2 = Price.builder().priority(1).price(20.0).build();

        Mockito.doReturn(priceCmd).when(mapper).toCmd(request);
        Mockito.doReturn(List.of(price1, price2)).when(priceRepository).getPrice(priceCmd);

        Price result = priceService.getPrice(request);

        Assertions.assertEquals(price2, result);
    }

}