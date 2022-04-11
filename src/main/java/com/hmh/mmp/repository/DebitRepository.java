package com.hmh.mmp.repository;

import com.hmh.mmp.entity.DebitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebitRepository extends JpaRepository<DebitEntity, Long> {
    List<DebitEntity> findAll(Long cardId);
}
