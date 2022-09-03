package com.hmh.mmp.service;

import com.hmh.mmp.dto.account.AccountDetailDTO;
import com.hmh.mmp.dto.account.AccountPagingDTO;
import com.hmh.mmp.dto.account.AccountSaveDTO;
import com.hmh.mmp.dto.account.AccountUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface AccountService {
    Long save(AccountSaveDTO accountSaveDTO) throws IOException;

    List<AccountDetailDTO> findAll(Long bankId);

    Page<AccountPagingDTO> paging(Pageable pageable);

    AccountDetailDTO findById(Long accountId);

    Long update(AccountUpdateDTO accountUpdateDTO) throws IOException;

    void delete(Long accountId);

    List<AccountDetailDTO> findData(Long memberId);
}
