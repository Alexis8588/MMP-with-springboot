package com.hmh.mmp.repository;

import com.hmh.mmp.entity.CashEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashRepository extends JpaRepository<CashEntity, Long> {

    List<CashEntity> findAll(Long memberId);
}
