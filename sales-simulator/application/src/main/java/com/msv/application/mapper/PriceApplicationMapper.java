package com.msv.application.mapper;

import com.msv.application.entity.PriceRequest;
import com.msv.domain.entity.PriceCmd;
import org.springframework.stereotype.Component;

@Component
public class PriceApplicationMapper {


    public PriceCmd toCmd(PriceRequest request){
        if(request == null) return null;
        return PriceCmd.builder()
                .productId(request.getProductId())
                .applicationDate(request.getApplicationDate())
                .chainId(request.getChainId())
                .build();
    }
}
