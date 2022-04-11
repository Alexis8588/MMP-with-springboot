package com.hmh.mmp.service;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.account.AccountDetailDTO;
import com.hmh.mmp.dto.account.AccountPagingDTO;
import com.hmh.mmp.dto.account.AccountSaveDTO;
import com.hmh.mmp.dto.account.AccountUpdateDTO;
import com.hmh.mmp.entity.AccountEntity;
import com.hmh.mmp.entity.BankEntity;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.repository.AccountRepository;
import com.hmh.mmp.repository.BankRepository;
import com.hmh.mmp.repository.CardRepository;
import com.hmh.mmp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository ar;
    private final BankRepository br;
    private final CardRepository cr;
    private final MemberRepository mr;

    @Override
    public Long save(AccountSaveDTO accountSaveDTO) throws IOException {
        MultipartFile accountPhoto = accountSaveDTO.getAccountPhoto();
        String accountPhotoName = accountPhoto.getOriginalFilename();
        accountPhotoName = System.currentTimeMillis() + "-" + accountPhotoName;

        String savePath = "/Users/myungha/Desktop/Github/MoneyManager_for_intel/src/main/resources/photo/account" + accountPhotoName;
        if (!accountPhoto.isEmpty()) {
            accountPhoto.transferTo(new File(savePath));
            accountSaveDTO.setAccountPhotoName(accountPhotoName);
        }

        // asset 설정
        if (accountSaveDTO.getPlusAsset() != null) {
            accountSaveDTO.setMinusAsset(0L);
        } else {
            accountSaveDTO.setPlusAsset(0L);
        }

        // bank에 금액 입력
        Long bankTotalAsset = br.findById(accountSaveDTO.getBankId()).get().getTotalAsset();
        bankTotalAsset = bankTotalAsset + accountSaveDTO.getPlusAsset() - accountSaveDTO.getMinusAsset();

        BankEntity bankEntity = br.findById(accountSaveDTO.getBankId()).get();

        // account저장으로 인해 생긴 금액의 차액을 정산하여 최종 금액으로 반영
        bankEntity.setTotalAsset(bankTotalAsset);

        AccountEntity accountEntity = AccountEntity.toSaveData(accountSaveDTO, bankEntity);

        Long accountId = ar.save(accountEntity).getId();

        return accountId;
    }

    @Override
    public List<AccountDetailDTO> findAll(Long bankId) {
        Optional<BankEntity> bankEntityOptional = br.findById(bankId); // 이렇게 하는 이유는 만약 account에서 그대로 list를 불러올 경우에 id 중복이 일어나게 되기 때문임.
        // 해당 List 관련해서도 불러야 하기는 하는데.
        BankEntity bankEntity = bankEntityOptional.get();

        List<AccountEntity> accountEntityList = bankEntity.getAccountEntityList();

//        List<AccountEntity> accountEntityList = ar.findAll(bankId);
        List<AccountDetailDTO> accountDetailDTOList = new ArrayList<>();

        for (AccountEntity ae:accountEntityList) {
            accountDetailDTOList.add(AccountDetailDTO.toMoveData(ae));
        }

        return accountDetailDTOList;
    }

    @Override
    public Page<AccountPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1) ? 0 : (page - 1);

        Page<AccountEntity> accountEntities = ar.findAll(PageRequest.of(page, PagingConst.A_PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<AccountPagingDTO> aList = accountEntities.map(
                account -> new AccountPagingDTO(account.getId(),
                        account.getAccountMemo(),
                        account.getAccountName(),
                        account.getAccountPhotoName(),
                        account.getMinusAsset(),
                        account.getPlusAsset(),
                        account.getBankEntity(),
                        account.getCalDate(),
                        account.getCalTime())
        );

        return aList;
    }

    @Override
    public AccountDetailDTO findById(Long accountId) {
        System.out.println("AccountServiceImpl.findById");
        AccountEntity accountEntity = ar.findById(accountId).get();
        AccountDetailDTO accountDetailDTO = AccountDetailDTO.toMoveData(accountEntity);

        return accountDetailDTO;
    }

    @Override
    public Long update(AccountUpdateDTO accountUpdateDTO) throws IOException {
        System.out.println("AccountServiceImpl.update");
        // 파일 저장
        MultipartFile accountPhoto = accountUpdateDTO.getAccountPhoto();
        String accountPhotoName = accountPhoto.getOriginalFilename();
        accountPhotoName = System.currentTimeMillis() + "-" + accountPhotoName;

        String savePath = "/Users/myungha/Desktop/Github/MoneyManager_for_intel/src/main/resources/photo/account" + accountPhotoName;
        if (!accountPhoto.isEmpty()) {
            accountPhoto.transferTo(new File(savePath));
            accountUpdateDTO.setAccountPhotoName(accountPhotoName);
        }

        // 자산 및 entity 호출
        Long bankTotalAsset = br.findById(accountUpdateDTO.getBankId()).get().getTotalAsset();
        BankEntity bankEntity = br.findById(accountUpdateDTO.getBankId()).get();

        // 금액 부분 설정
        if (accountUpdateDTO.getMinusAsset() != 0) {
            accountUpdateDTO.setPlusAsset(0L);
        } else {
            accountUpdateDTO.setMinusAsset(0L);
        }

        // bank에 금액 설정
        bankTotalAsset = bankTotalAsset + accountUpdateDTO.getPlusAsset() - accountUpdateDTO.getMinusAsset();
        bankEntity.setTotalAsset(bankTotalAsset);

        // 데이터 전달
        AccountEntity accountEntity = AccountEntity.toUpdateData(accountUpdateDTO);
        Long accountId = ar.save(accountEntity).getId();

        return accountId;
    }

    @Override
    public void delete(Long accountId) {
        System.out.println("AccountServiceImpl.delete");
        AccountEntity accountEntity = ar.findById(accountId).get();
        ar.delete(accountEntity);

    }

    @Override
    public List<AccountDetailDTO> findData(Long memberId) { // 해당 프로세스 관련해서 제대로 좀더 익숙해지고 이해해야함.
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId);
        MemberEntity memberEntity = memberEntityOptional.get();

        List<AccountEntity> accountEntityList = memberEntity.getAccountEntityList();

        List<AccountDetailDTO> accountDetailDTOList = new ArrayList<>();

        for (AccountEntity accountEntity:accountEntityList) {
            accountDetailDTOList.add(AccountDetailDTO.toMoveData(accountEntity));
        }

        return accountDetailDTOList;
    }
}
