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
public class PriceCmd {
    private LocalDateTime applicationDate;
    private String productId;
    private String chainId;
}
