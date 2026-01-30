package com.msv.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Price {
    private String brandId;

    private String productId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private double price;

    private String currency;
}
