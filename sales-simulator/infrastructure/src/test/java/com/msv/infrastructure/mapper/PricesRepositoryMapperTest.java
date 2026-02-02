package com.msv.infrastructure.mapper;

import com.msv.domain.entity.Price;
import com.msv.infrastructure.entity.PriceEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

class PricesRepositoryMapperTest {

    private final PricesRepositoryMapper pricesRepositoryMapper = new PricesRepositoryMapper();

    private static Stream<Arguments> testParams() {
        return Stream.of(
                Arguments.of(
                        PriceEntity.builder()
                                .brandId("1")
                                .productId("35455")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59))
                                .price(35.50)
                                .currency("EUR")
                                .priceList(1)
                                .priority(0)
                                .build(),
                        Price.builder()
                                .brandId("1")
                                .productId("35455")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59))
                                .price(35.50)
                                .currency("EUR")
                                .build()
                ),
                Arguments.of(
                        PriceEntity.builder()
                                .brandId("2")
                                .productId("12345")
                                .startDate(LocalDateTime.of(2021, Month.JANUARY, 1, 10, 0))
                                .endDate(LocalDateTime.of(2021, Month.JANUARY, 1, 20, 0))
                                .price(20.0)
                                .currency("USD")
                                .build(),
                        Price.builder()
                                .brandId("2")
                                .productId("12345")
                                .startDate(LocalDateTime.of(2021, Month.JANUARY, 1, 10, 0))
                                .endDate(LocalDateTime.of(2021, Month.JANUARY, 1, 20, 0))
                                .price(20.0)
                                .currency("USD")
                                .build()
                ),
                Arguments.of(
                        PriceEntity.builder().build(),
                        Price.builder().price(0.0).build()
                ),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("testParams")
    void mapToDomain(PriceEntity entity, Price expected) {
        Price result = pricesRepositoryMapper.mapToDomain(entity);
        Assertions.assertEquals(expected, result);
    }
}
