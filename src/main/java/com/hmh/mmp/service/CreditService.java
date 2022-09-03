package com.hmh.mmp.service;

import com.hmh.mmp.dto.credit.CreditDetailDTO;
import com.hmh.mmp.dto.credit.CreditPagingDTO;
import com.hmh.mmp.dto.credit.CreditSaveDTO;
import com.hmh.mmp.dto.credit.CreditUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CreditService {
    Page<CreditPagingDTO> paging(Pageable pageable);

    List<CreditDetailDTO> findAll(Long cardId);

    Long save(CreditSaveDTO creditSaveDTO);

    CreditDetailDTO findById(Long creditId);

    void delete(Long creditId);

    Long update(CreditUpdateDTO creditUpdateDTO);

    List<CreditDetailDTO> findData(Long memberId);
}
