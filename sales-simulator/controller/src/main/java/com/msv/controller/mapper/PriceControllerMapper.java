package com.msv.controller.mapper;

import com.msv.controller.entity.PriceResponse;
import com.msv.domain.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceControllerMapper {

    public PriceResponse toResponse(Price price){
        return PriceResponse.builder()
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .build();
    }

}
