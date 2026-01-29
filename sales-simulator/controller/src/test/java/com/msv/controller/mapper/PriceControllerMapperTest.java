package com.msv.controller.mapper;

import com.msv.controller.entity.PriceResponse;
import com.msv.domain.Price;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceControllerMapperTest {

    private final PriceControllerMapper mapper = new PriceControllerMapper();

    private static Stream<Arguments> testParams() {
        return Stream.of(
                Arguments.of(
                        Price.builder()
                                .brandId("1")
                                .productId("35455")
                                .price(10.0)
                                .currency("EUR")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build(),
                        PriceResponse.builder()
                                .brandId("1")
                                .productId("35455")
                                .price(10.0)
                                .currency("EUR")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build()
                ), Arguments.of(
                        Price.builder()
                                .productId("35455")
                                .price(10.0)
                                .currency("EUR")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build(),
                        PriceResponse.builder()
                                .productId("35455")
                                .price(10.0)
                                .currency("EUR")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build()
                ),
                Arguments.of(
                        Price.builder()
                                .brandId("1")
                                .price(10.0)
                                .currency("EUR")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build(),
                        PriceResponse.builder()
                                .brandId("1")
                                .price(10.0)
                                .currency("EUR")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build()
                ), Arguments.of(
                        Price.builder()
                                .brandId("1")
                                .productId("35455")
                                .currency("EUR")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build(),
                        PriceResponse.builder()
                                .brandId("1")
                                .productId("35455")
                                .currency("EUR")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build()
                ), Arguments.of(
                        Price.builder()
                                .brandId("1")
                                .productId("35455")
                                .price(10.0)
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build(),
                        PriceResponse.builder()
                                .brandId("1")
                                .productId("35455")
                                .price(10.0)
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build()
                ), Arguments.of(
                        Price.builder()
                                .brandId("1")
                                .productId("35455")
                                .price(10.0)
                                .currency("EUR")
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build(),
                        PriceResponse.builder()
                                .brandId("1")
                                .productId("35455")
                                .price(10.0)
                                .currency("EUR")
                                .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 23, 59))
                                .build()
                ), Arguments.of(
                        Price.builder()
                                .brandId("1")
                                .productId("35455")
                                .price(10.0)
                                .currency("EUR")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .build(),
                        PriceResponse.builder()
                                .brandId("1")
                                .productId("35455")
                                .price(10.0)
                                .currency("EUR")
                                .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0))
                                .build()
                ), Arguments.of(
                        Price.builder()
                                .build(),
                        PriceResponse.builder()
                                .build()
                )

        );
    }

    @ParameterizedTest
    @MethodSource("testParams")
    void toResponse(Price price, PriceResponse expected) {
        assertEquals(expected, mapper.toResponse(price));
    }
}