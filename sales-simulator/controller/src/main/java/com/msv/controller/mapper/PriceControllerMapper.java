package com.msv.controller.mapper;

import com.msv.controller.entity.PriceResponse;
import com.msv.domain.entity.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceControllerMapper {

    public PriceResponse toResponse(Price price){
        if(price == null) return null;
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
