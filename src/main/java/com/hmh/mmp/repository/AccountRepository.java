package com.hmh.mmp.repository;

import com.hmh.mmp.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findAll(Long bankId);
}
