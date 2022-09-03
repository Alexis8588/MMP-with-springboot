package com.hmh.mmp.service;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.balance.BalanceDetailDTO;
import com.hmh.mmp.dto.balance.BalancePagingDTO;
import com.hmh.mmp.dto.balance.BalanceSaveDTO;
import com.hmh.mmp.dto.balance.BalanceUpdateDTO;
import com.hmh.mmp.entity.BalanceEntity;
import com.hmh.mmp.entity.CashEntity;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.repository.BalanceRepository;
import com.hmh.mmp.repository.CashRepository;
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

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService{
    private final BalanceRepository bar;
    private final CashRepository csr;
    private final MemberRepository mr;

    @Override
    public List<BalanceDetailDTO> findAll(Long cashId) {
        Optional<CashEntity> cashEntityOptional = csr.findById(cashId);
        CashEntity cashEntity = cashEntityOptional.get();

        List<BalanceEntity> balanceEntityList = cashEntity.getBalanceEntityList();

//        List<BalanceEntity> balanceEntityList = bar.findAll(cashId);
        List<BalanceDetailDTO> balanceDetailDTOList = new ArrayList<>();

        for (BalanceEntity be: balanceEntityList) {
            balanceDetailDTOList.add(BalanceDetailDTO.toMoveData(be));
        }

        return balanceDetailDTOList;
    }

    @Override
    public Page<BalancePagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1) ? 0 : (page - 1);

        Page<BalanceEntity> balanceEntities = bar.findAll(PageRequest.of(page, PagingConst.BA_PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<BalancePagingDTO> balancePage = balanceEntities.map(
                balance -> new BalancePagingDTO(balance.getId(),
                        balance.getBalanceName(),
                        balance.getBalanceMemo(),
                        balance.getBalancePhotoName(),
                        balance.getMinusAsset(),
                        balance.getPlusAsset(),
                        balance.getNowTime())
        );

        return balancePage;
    }

    @Override
    public BalanceDetailDTO findById(Long balanceId) {
        System.out.println("BalanceServiceImpl.findById");

        BalanceEntity balanceEntity = bar.findById(balanceId).get();
        BalanceDetailDTO balanceDetailDTO = BalanceDetailDTO.toMoveData(balanceEntity);

        return balanceDetailDTO;
    }

    @Override
    public Long update(BalanceUpdateDTO balanceUpdateDTO) throws IOException {
        System.out.println("BalanceServiceImpl.update");
        // 사진 호출
        MultipartFile balancePhoto = balanceUpdateDTO.getBalancePhoto();
        String balancePhotoName = balancePhoto.getOriginalFilename();
        balancePhotoName = System.currentTimeMillis() + "-" + balancePhotoName;

        String savePath = "" + balancePhotoName;
        if (!balancePhoto.isEmpty()) {
            balancePhoto.transferTo(new File(savePath));
            balanceUpdateDTO.setBalancePhotoName(balancePhotoName);
        }

        // 금액을 현금 계좌에 반영
        CashEntity cashEntity = csr.findById(balanceUpdateDTO.getCashId()).get(); // cash 항목 꺼내기
        Long totalAsset = cashEntity.getTotalAsset();// cash의 해당 총 금액
        totalAsset = totalAsset + balanceUpdateDTO.getPlusAsset() - balanceUpdateDTO.getMinusAsset();
        cashEntity.setTotalAsset(totalAsset);

        csr.save(cashEntity); // 해당 항목 넣어서 업데이트

        // 데이터 저장
        BalanceEntity balanceEntity = BalanceEntity.toUpdateData(balanceUpdateDTO);
        Long balanceId = bar.save(balanceEntity).getId();
        return balanceId;
    }

    @Override
    public void delete(Long balanceId) {
        System.out.println("BalanceServiceImpl.delete");
        BalanceEntity balanceEntity = bar.findById(balanceId).get();

        bar.delete(balanceEntity);
    }

    @Override
    public Long save(BalanceSaveDTO balanceSaveDTO) throws IOException {
        System.out.println("BalanceServiceImpl.save");
        // 사진 저장
        MultipartFile balancePhoto = balanceSaveDTO.getBalancePhoto();
        String balancePhotoName = balancePhoto.getOriginalFilename();
        balancePhotoName = System.currentTimeMillis() + "-" + balancePhotoName;

        String savePath = "" + balancePhotoName;

        if (!balancePhoto.isEmpty()) {
            balancePhoto.transferTo(new File(savePath));
            balanceSaveDTO.setBalancePhotoName(balancePhotoName);
        }

        // 금액을 현금 계좌에 반영
        CashEntity cashEntity = csr.findById(balanceSaveDTO.getCashId()).get(); // cash 항목 꺼내기
        Long totalAsset = cashEntity.getTotalAsset();// cash의 해당 총 금액
        totalAsset = totalAsset + balanceSaveDTO.getPlusAsset() - balanceSaveDTO.getMinusAsset();
        cashEntity.setTotalAsset(totalAsset);

        csr.save(cashEntity); // 해당 항목 넣어서 업데이트


        //데이터 저장
        BalanceEntity balanceEntity = BalanceEntity.toSaveData(balanceSaveDTO, cashEntity);

        Long balanceId = bar.save(balanceEntity).getId();
        return balanceId;
    }

    @Override
    public List<BalanceDetailDTO> findData(Long memberId) {
        MemberEntity memberEntity = mr.findById(memberId).get();
        List<BalanceEntity> balanceEntityList = memberEntity.getBalanceEntityList();
        List<BalanceDetailDTO> balanceDetailDTOList = new ArrayList<>();

        for (BalanceEntity balanceEntity: balanceEntityList) {
            balanceDetailDTOList.add(BalanceDetailDTO.toMoveData(balanceEntity));
        }

        return balanceDetailDTOList;
    }
}
