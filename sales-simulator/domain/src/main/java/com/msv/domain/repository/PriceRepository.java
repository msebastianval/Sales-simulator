package com.msv.domain.repository;

import com.msv.domain.entity.Price;
import com.msv.domain.entity.PriceCmd;

import java.util.List;

public interface PriceRepository {

    List<Price> getPrice(PriceCmd cmd);
}
