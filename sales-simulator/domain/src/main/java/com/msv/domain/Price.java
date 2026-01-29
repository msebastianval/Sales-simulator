package com.msv.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class Price {
    private String brandId;

    private String productId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private double price;

    private String currency;
}
