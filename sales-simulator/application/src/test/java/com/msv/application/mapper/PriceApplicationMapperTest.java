package com.msv.application.mapper;

import com.msv.application.entity.PriceRequest;
import com.msv.domain.entity.PriceCmd;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

class PriceApplicationMapperTest {
    private final PriceApplicationMapper priceApplicationMapper = new PriceApplicationMapper();
    private static Stream<Arguments> testParams() {

        return Stream.of(
                Arguments.of(
                        PriceRequest.builder()
                                .applicationDate(LocalDateTime.of(2020, Month.DECEMBER, 1, 0, 0))
                                .chainId("1")
                                .productId("1234")
                                .build(),
                        PriceCmd.builder()
                                .applicationDate(LocalDateTime.of(2020, Month.DECEMBER, 1, 0, 0))
                                .chainId("1")
                                .productId("1234")
                                .build()
                ),Arguments.of(
                        null,
                        null
                ), Arguments.of(
                        PriceRequest.builder()
                                .chainId("1")
                                .productId("1234")
                                .build(),
                        PriceCmd.builder()
                                .chainId("1")
                                .productId("1234")
                                .build()
                ), Arguments.of(
                        PriceRequest.builder()
                                .applicationDate(LocalDateTime.of(2020, Month.DECEMBER, 1, 0, 0))
                                .productId("1234")
                                .build(),
                        PriceCmd.builder()
                                .applicationDate(LocalDateTime.of(2020, Month.DECEMBER, 1, 0, 0))
                                .productId("1234")
                                .build()
                ), Arguments.of(
                        PriceRequest.builder()
                                .applicationDate(LocalDateTime.of(2020, Month.DECEMBER, 1, 0, 0))
                                .chainId("1")
                                .build(),
                        PriceCmd.builder()
                                .applicationDate(LocalDateTime.of(2020, Month.DECEMBER, 1, 0, 0))
                                .chainId("1")
                                .build()
                ), Arguments.of(
                        PriceRequest.builder()
                                .build(),
                        PriceCmd.builder()
                                .build()
                )
        );
    }
    @ParameterizedTest
    @MethodSource("testParams")
    void toCmd(PriceRequest request, PriceCmd expected) {
        PriceCmd result = priceApplicationMapper.toCmd(request);
        Assertions.assertEquals(expected, result);
    }
}