package com.msv.infrastructure;

import com.msv.domain.entity.Price;
import com.msv.domain.entity.PriceCmd;
import com.msv.infrastructure.entity.PriceEntity;
import com.msv.infrastructure.mapper.PricesRepositoryMapper;
import com.msv.infrastructure.repository.SpringDataPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceH2RepositoryTest {

    @Mock
    private SpringDataPriceRepository springDataPriceRepository;

    @Mock
    private PricesRepositoryMapper mapper;

    @InjectMocks
    private PriceH2Repository priceH2Repository;

    private PriceCmd priceCmd;
    private PriceEntity priceEntity;
    private Price price;

    @BeforeEach
    void setUp() {
        priceCmd = PriceCmd.builder()
                .productId("35455")
                .chainId("1")
                .applicationDate(LocalDateTime.now())
                .build();

        priceEntity = PriceEntity.builder()
                .productId("35455")
                .brandId("1")
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .price(35.50)
                .currency("EUR")
                .priority(1)
                .priceList(1)
                .build();

        price = Price.builder()
                .productId("35455")
                .brandId("1")
                .startDate(priceEntity.getStartDate())
                .endDate(priceEntity.getEndDate())
                .price(35.50)
                .currency("EUR")
                .build();
    }

    @Test
    void getPrice_whenPriceExists_shouldReturnMappedPrice() {
        doReturn(List.of(priceEntity)).when(springDataPriceRepository).findApplicablePrices(
                priceCmd.getProductId(),
                priceCmd.getChainId(),
                priceCmd.getApplicationDate()
        );
        
        doReturn(price).when(mapper).mapToDomain(priceEntity);

        Price result = priceH2Repository.getPrice(priceCmd);

        assertNotNull(result);
        assertEquals(price.getProductId(), result.getProductId());
        assertEquals(price.getBrandId(), result.getBrandId());
        assertEquals(price.getPrice(), result.getPrice());
        verify(springDataPriceRepository).findApplicablePrices(priceCmd.getProductId(), priceCmd.getChainId(), priceCmd.getApplicationDate());
        verify(springDataPriceRepository, times(1)).findApplicablePrices(any(), any(), any());
        verify(mapper).mapToDomain(priceEntity);
        verify(mapper, times(1)).mapToDomain(any());
    }

    @Test
    void getPrice_whenPriceDoesNotExist_shouldReturnNull() {
        doReturn(Collections.emptyList()).when(springDataPriceRepository).findApplicablePrices(
                priceCmd.getProductId(),
                priceCmd.getChainId(),
                priceCmd.getApplicationDate()
        );

        Price result = priceH2Repository.getPrice(priceCmd);

        assertNull(result);
        verify(springDataPriceRepository).findApplicablePrices(priceCmd.getProductId(), priceCmd.getChainId(), priceCmd.getApplicationDate());
        verify(springDataPriceRepository, times(1)).findApplicablePrices(any(), any(), any());
        verifyNoInteractions(mapper);
    }
}
