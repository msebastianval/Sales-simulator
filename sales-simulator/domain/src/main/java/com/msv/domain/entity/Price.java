package com.msv.domain.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Price {
    private String brandId;

    private String productId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Double price;

    private String currency;
}
