package com.msv.infrastructure.adapter;

import com.msv.domain.entity.Price;
import com.msv.domain.entity.PriceCmd;
import com.msv.domain.repository.PriceRepository;
import com.msv.infrastructure.entity.PriceEntity;
import com.msv.infrastructure.mapper.PricesRepositoryMapper;
import com.msv.infrastructure.repository.SpringDataPriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PriceH2Adapter implements PriceRepository {

    private final SpringDataPriceRepository springDataPriceRepository;

    private final PricesRepositoryMapper mapper;

    @Override
    public Price getPrice(PriceCmd cmd) {
        List<PriceEntity> prices = springDataPriceRepository.findApplicablePrices(
                cmd.getProductId(),
                cmd.getChainId(),
                cmd.getApplicationDate()
        );

        return prices.stream()
                .findFirst()
                .map(mapper::mapToDomain)
                .orElse(null);
    }

}
