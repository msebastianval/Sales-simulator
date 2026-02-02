package com.msv.application.adapter;

import com.msv.application.entity.PriceRequest;
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


@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceApplicationMapper mapper;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Test
    void getPrice() throws Exception {
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
        Mockito.doReturn(price).when(priceRepository).getPrice(priceCmd);

        Assertions.assertEquals(expected, priceService.getPrice(request));
    }

    @Test
    void getPriceNull() throws Exception {
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
        Mockito.doReturn(null).when(priceRepository).getPrice(priceCmd);

        Assertions.assertThrowsExactly(Exception.class, () -> priceService.getPrice(request));
    }

}