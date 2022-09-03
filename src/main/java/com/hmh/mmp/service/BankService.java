package com.hmh.mmp.service;

import com.hmh.mmp.dto.bank.BankDetailDTO;
import com.hmh.mmp.dto.bank.BankPagingDTO;
import com.hmh.mmp.dto.bank.BankSaveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BankService {
    Long save(BankSaveDTO bankSaveDTO) throws IOException;
    List<BankDetailDTO> findAll(Long memberId);

    BankDetailDTO findById(Long bankId);

    Page<BankPagingDTO> paging(Pageable pageable);

    Long update(BankDetailDTO bankDetailDTO) throws IOException;

    void delete(Long bankId);
}
