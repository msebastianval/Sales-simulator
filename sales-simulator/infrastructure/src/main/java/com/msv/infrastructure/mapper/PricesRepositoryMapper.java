package com.msv.infrastructure.mapper;

import com.msv.domain.entity.Price;
import com.msv.infrastructure.entity.PriceEntity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class PricesRepositoryMapper {

    public Price mapToDomain(PriceEntity entity) {
        return Price.builder()
                .brandId(entity.getBrandId())
                .productId(entity.getProductId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .build();
    }
}
