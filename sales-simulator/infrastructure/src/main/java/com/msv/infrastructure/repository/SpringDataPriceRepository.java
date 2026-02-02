package com.msv.infrastructure.repository;

import com.msv.infrastructure.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE p.productId = :productId AND p.brandId = :brandId " +
            "AND :applicationDate >= p.startDate AND :applicationDate <= p.endDate " +
            "ORDER BY p.priority DESC")
    List<PriceEntity> findApplicablePrices(@Param("productId") String productId,
                                          @Param("brandId") String brandId,
                                          @Param("applicationDate") LocalDateTime applicationDate);
}
