package com.hmh.mmp.repository;

import com.hmh.mmp.entity.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {
    List<BalanceEntity> findAll(Long cashId);
}
