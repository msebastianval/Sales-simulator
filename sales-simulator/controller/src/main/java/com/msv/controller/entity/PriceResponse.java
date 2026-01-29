package com.msv.controller.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PriceResponse {
    private String brandId;

    private String productId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private double price;

    private String currency;

}
