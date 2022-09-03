package com.hmh.mmp.service;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.bank.BankDetailDTO;
import com.hmh.mmp.dto.bank.BankPagingDTO;
import com.hmh.mmp.dto.bank.BankSaveDTO;
import com.hmh.mmp.entity.BankEntity;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.repository.BankRepository;
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
public class BankServiceImp implements BankService{
    private final BankRepository br;
    private final MemberRepository mr;

    @Override
    public Long save(BankSaveDTO bankSaveDTO) throws IOException {
        Optional<MemberEntity> memberEntityOptional = mr.findById(bankSaveDTO.getMemberId());
        MemberEntity memberEntity = memberEntityOptional.get();

        MultipartFile bankPhoto = bankSaveDTO.getBankPhoto();
        String bankPhotoName = bankPhoto.getOriginalFilename();
        bankPhotoName = System.currentTimeMillis() + "-" + bankPhotoName;

        String savePath = "D:\\GitHub\\MoneyManager_for_intel\\src\\main\\resources\\photo\\bank" + bankPhotoName;

        if (!bankPhoto.isEmpty()) {
            bankPhoto.transferTo(new File(savePath));
            bankSaveDTO.setBankPhotoName(bankPhotoName);
        }

        Long asset = bankSaveDTO.getTotalAsset();
        if (asset == null) {
            bankSaveDTO.setTotalAsset(0);
        }

        BankEntity bankEntity = BankEntity.saveBank(bankSaveDTO, memberEntity);

        System.out.println("BankEntity.MemberId : " + bankEntity.getId());

//        bankSaveDTO.setMemberId(bankEntity.getId()); // 로그인 중인 고객의 정보 가지고 오기

//        MultipartFile bankPhoto = bankSaveDTO.get; // photo 를 넣어야 하나? 계좌당?

//        bankEntity.getId();
        // 위에서 memberEntity를 통해서 데이터를 집어 넣었기 때문에 문제 없음
        // 또한 bankSaveDTO에서의 memberId는 BankController에서 후처리로 값을 넣어줌.
        return br.save(bankEntity).getId(); //이 Id는 어디서 나온 것일까..... // 현재 여기 까지 진행...
    }

    @Override
    public List<BankDetailDTO> findAll(Long memberId) {
        // 이부분 수정 필요.
//        List<BankEntity> bankEntityList = br.findAll(memberId);
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId); // findAll이 아닌 해당 아이디만 조회해서 가지고 오는 것음.
        MemberEntity memberEntity = memberEntityOptional.get();

        List<BankEntity> bankEntityList = memberEntity.getBankEntityList();

        List<BankDetailDTO> bankDetailDTOList = new ArrayList<>();

        for (BankEntity b: bankEntityList) {
//            bankDetailDTOList.add(bankEntityList.get(b));
            // 해당 메서드에 대해서 toBankDetail 관련 메서드 필요.
            bankDetailDTOList.add(BankDetailDTO.toBankDetail(b));
        }
        return bankDetailDTOList;
    }

    @Override
    public BankDetailDTO findById(Long bankId) {
        Optional<BankEntity> bankDetail = br.findById(bankId);

        // bankId로 조회를 해야하나?
        // findAll은 memberId로 조회해야하지만 이건 다르지...
        BankDetailDTO bankDetailDTO = null;

        // bankDetail 로 명명해서 가져온 데이터(Entity에 있는 데이터)를 BankDetailDTO에 담아서 보내야지...
        if (bankDetail.isEmpty()) {
            return null;
        } else {
            bankDetailDTO = BankDetailDTO.toBankDetail(bankDetail.get());
        }

        return bankDetailDTO;
    }

    @Override
    public Page<BankPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber(); //페이지 현재 쪽수에 대한 정보

        page = (page == 1) ? 0 : (page - 1);

        Page<BankEntity> bankEntityList = br.findAll(PageRequest.of(page, PagingConst.B_PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

        Page<BankPagingDTO> pList = bankEntityList.map( // getMemberId의 타입에 대해서 정의가 필요함. Entity의 ManyToOne을 사용하는 방법에 대해서 필요함.
                bank -> new BankPagingDTO(bank.getId(),
                        bank.getMemberEntity().getId(),
                        bank.getBankName(),
                        bank.getBankMemo(),
                        bank.getTotalAsset())
        );

        return pList;
    }

    @Override
    public Long update(BankDetailDTO bankDetailDTO) throws IOException {
        MemberEntity memberEntity = mr.findById(bankDetailDTO.getMemberId()).get();
        // banktotalAsset 관련 수식을 만들어서 넣어야함.
        Long beforeAsset = br.findById(bankDetailDTO.getBankId()).get().getTotalAsset();
        if (bankDetailDTO.getTotalAsset() > 0) {
            beforeAsset = beforeAsset + bankDetailDTO.getTotalAsset();
            bankDetailDTO.setTotalAsset(beforeAsset);
        } else {
            beforeAsset = beforeAsset - bankDetailDTO.getTotalAsset();
            bankDetailDTO.setTotalAsset(beforeAsset);
        }

        // 이미지 넣었나? 확인
        MultipartFile bankPhoto = bankDetailDTO.getBankPhoto();
        String bankPhotoName = bankPhoto.getOriginalFilename();
        bankPhotoName = System.currentTimeMillis() + "-" + bankPhotoName;

        String savePath = "D:\\GitHub\\MoneyManager_for_intel\\src\\main\\resources\\photo\\bank" + bankPhotoName;

        if (!bankPhoto.isEmpty()) {
            bankPhoto.transferTo(new File(savePath));
            bankDetailDTO.setBankPhotoName(bankPhotoName);
        }
        // 변경이 되었는지 봐야함

        BankEntity bankEntity = BankEntity.updateBank(bankDetailDTO, memberEntity);
        Long bankId = br.save(bankEntity).getId();

        return bankId;
    }

    @Override
    public void delete(Long bankId) {
        System.out.println("BankServiceImp.delete");
        BankEntity bankEntity = br.findById(bankId).get();

        br.delete(bankEntity);
    }
}
