package com.hmh.mmp.service;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.cash.CashDetailDTO;
import com.hmh.mmp.dto.cash.CashPagingDTO;
import com.hmh.mmp.dto.cash.CashSaveDTO;
import com.hmh.mmp.dto.cash.CashUpdateDTO;
import com.hmh.mmp.entity.CashEntity;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.repository.CashRepository;
import com.hmh.mmp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService{
    private final CashRepository csr;
    private final MemberRepository mr;

    @Override
    public Long save(CashSaveDTO cashSaveDTO) throws IOException {
        Optional<MemberEntity> memberEntityOptional = mr.findById(cashSaveDTO.getMemberId());
        MemberEntity memberEntity = memberEntityOptional.get();

        MultipartFile cashPhoto = cashSaveDTO.getCashPhoto();
        String cashPhotoName = cashPhoto.getOriginalFilename();
        cashPhotoName = System.currentTimeMillis() + "-" + cashPhotoName;

        String savePath = "/Users/myungha/Desktop/Github/MoneyManager_for_intel/src/main/resources/photo/cash" + cashPhotoName;

        if (!cashPhoto.isEmpty()) {
            cashPhoto.transferTo(new File(savePath));
            cashSaveDTO.setCashPhotoName(cashPhotoName);
        }

        CashEntity cashEntity = CashEntity.toSaveCash(cashSaveDTO, memberEntity);

        CashEntity cashSave = csr.save(cashEntity);

        Long cashId = cashSave.getId();

        return cashId;
    }

    @Override
    public List<CashDetailDTO> findAll(Long memberId) {
        List<CashEntity> cashEntityList = csr.findAll(memberId); // ?????? ????????????? ????????? memberId??? ??? ????????????.
        List<CashDetailDTO> cashDetailDTOList = new ArrayList<>();

        for (CashEntity ce: cashEntityList) {
            cashDetailDTOList.add(CashDetailDTO.toMoveData(ce));
        }

        return cashDetailDTOList;
    }

    @Override
    public Page<CashPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber(); // ??? ????????? ????????? ????????? ???.

        page = (page == 1) ? 0 : (page - 1); // page??? 1?????? 0?????? ???????????? ????????? ???????????? -1??? ?????? (?????? page -1)??? ?????? ?????????

        Page<CashEntity> cashEntityPage = csr.findAll(PageRequest.of(page, PagingConst.CS_PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

        Page<CashPagingDTO> cashPagingDTOS = cashEntityPage.map(
                cash -> new CashPagingDTO(cash.getId(),
                        cash.getCashMemo(),
                        cash.getCashName(),
                        cash.getCashPhotoName(),
                        cash.getTotalAsset(),
                        cash.getNowTime())
        );

        return cashPagingDTOS;
    }

    @Override
    public CashDetailDTO findById(Long cashId) {
        System.out.println("CashServiceImpl.findById");

        CashEntity cashEntity = csr.findById(cashId).get();
        CashDetailDTO cashDetailDTO = null;

        if (cashEntity == null) {
            // ????????? ?????? ????????? ?????? ??? ????????????.
            BindingResult bindingResult = null;
//            bindingResult.hasErrors("???????????? ????????????");

        } else {
            cashDetailDTO = CashDetailDTO.toMoveData(cashEntity);
        }

        return cashDetailDTO;
    }

    @Override
    public Long update(CashUpdateDTO cashUpdateDTO) {
        Long beforeAsset = csr.findById(cashUpdateDTO.getCashId()).get().getTotalAsset();
        Long afterAsset = cashUpdateDTO.getTotalAsset();
        if (beforeAsset != afterAsset) {
            // ???????????? ????????? ????????? ???????????? ????????? ???.
            Long totalAsset = beforeAsset - afterAsset;
            // ????????? ????????? ??????... ??? ?????????....
        }

        CashEntity cashEntity = CashEntity.toUpdateData(cashUpdateDTO);
        Long cashId = csr.save(cashEntity).getId();

        return cashId;
    }

    @Override
    public void delete(Long cashId) {
        System.out.println("CashServiceImpl.delete");
        // ???????????? ?????? ?????? ?????? ???????????? ????????? (??)

        CashEntity cashEntity = csr.findById(cashId).get();
        csr.delete(cashEntity);

    }
}
