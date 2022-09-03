package com.hmh.mmp.service;

import com.hmh.mmp.dto.balance.BalanceDetailDTO;
import com.hmh.mmp.dto.balance.BalancePagingDTO;
import com.hmh.mmp.dto.balance.BalanceSaveDTO;
import com.hmh.mmp.dto.balance.BalanceUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BalanceService {
    List<BalanceDetailDTO> findAll(Long cashId);

    Page<BalancePagingDTO> paging(Pageable pageable);

    BalanceDetailDTO findById(Long balanceId);

    Long update(BalanceUpdateDTO balanceUpdateDTO) throws IOException;

    void delete(Long balanceId);

    Long save(BalanceSaveDTO balanceSaveDTO) throws IOException;

    List<BalanceDetailDTO> findData(Long memberId);
}
