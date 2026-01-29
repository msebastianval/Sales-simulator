package com.msv.application.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode
public class PriceRequest {
    private LocalDateTime applicationDate;
    private String productId;
    private String chainId;
}
