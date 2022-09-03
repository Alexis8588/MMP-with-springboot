package com.hmh.mmp.repository;

import com.hmh.mmp.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    List<CardEntity> findAll(Long memberId);
}
