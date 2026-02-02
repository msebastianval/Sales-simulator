package com.msv.domain.repository;

import com.msv.domain.entity.Price;
import com.msv.domain.entity.PriceCmd;
public interface PriceRepository {

    Price getPrice(PriceCmd cmd);
}
