package com.msv.controller.mapper;

import com.msv.controller.model.PriceResponse;
import com.msv.domain.entity.Price;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class PriceControllerMapper {

    public PriceResponse toResponse(Price price){
        if(price == null) return null;
        PriceResponse response = new PriceResponse();
        response.setStartDate(price.getStartDate() != null ? price.getStartDate().atOffset(ZoneOffset.UTC) : null);
        response.setEndDate(price.getEndDate() != null ? price.getEndDate().atOffset(ZoneOffset.UTC) : null);
        response.setProductId(price.getProductId());
        response.setBrandId(price.getBrandId());
        response.setPrice(price.getPrice());
        response.setCurrency(price.getCurrency());
        return response;
    }

}
