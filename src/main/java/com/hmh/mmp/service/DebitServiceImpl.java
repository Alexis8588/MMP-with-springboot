package com.hmh.mmp.service;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.debit.DebitDetailDTO;
import com.hmh.mmp.dto.debit.DebitPagingDTO;
import com.hmh.mmp.dto.debit.DebitSaveDTO;
import com.hmh.mmp.dto.debit.DebitUpdateDTO;
import com.hmh.mmp.entity.BankEntity;
import com.hmh.mmp.entity.CardEntity;
import com.hmh.mmp.entity.DebitEntity;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.repository.BankRepository;
import com.hmh.mmp.repository.CardRepository;
import com.hmh.mmp.repository.DebitRepository;
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
public class DebitServiceImpl implements DebitService {
    private final CardRepository crr;
    private final BankRepository br;
    private final DebitRepository dr;
    private final MemberRepository mr;

    @Override
    public Page<DebitPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();

        page = (page == 1) ? 0 : (page - 1);
        Page<DebitEntity> debitEntities = dr.findAll(PageRequest.of(page, PagingConst.DR_PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id'")));
        Page<DebitPagingDTO> debitPage = debitEntities.map(
                debit -> new DebitPagingDTO(debit.getId(),
                        debit.getDebitGet(),
                        debit.getDebitMemo(),
                        debit.getDebitName(),
                        debit.getMinusAsset(),
                        debit.getAccount(),
                        debit.getDebitPhotoName())
        );

        return debitPage;
    }

    @Override
    public List<DebitDetailDTO> findAll(Long cardId) {
        Optional<CardEntity> cardEntityOptional = crr.findById(cardId);
        CardEntity cardEntity = cardEntityOptional.get();

        List<DebitEntity> debitEntities = cardEntity.getDebitEntityList();

        //        List<DebitEntity> debitEntities = dr.findAll(cardId);
        List<DebitDetailDTO> debitDetailDTOList = new ArrayList<>();

        for (DebitEntity de: debitEntities) {
            debitDetailDTOList.add(DebitDetailDTO.toMoveData(de));
        }

        return debitDetailDTOList;
    }

    @Override
    public Long save(DebitSaveDTO debitSaveDTO) throws IOException {
        System.out.println("DebitServiceImpl.save");
        // 파일 저장 화
        MultipartFile debitPhoto = debitSaveDTO.getDebitPhoto();
        String debitPhotoName = debitPhoto.getOriginalFilename();
        debitPhotoName = System.currentTimeMillis() + "-" + debitPhotoName;
        String savePath = "/Users/myungha/Desktop/Github/MoneyManager_for_intel/src/main/resources/photo/debit" + debitPhotoName;

        if(!debitPhoto.isEmpty()) {
            debitPhoto.transferTo(new File(savePath));
            debitSaveDTO.setDebitPhotoName(debitPhotoName);
        }

        double minusAsset = debitSaveDTO.getMinusAsset();

        if ((Double)debitSaveDTO.getDebitGet() == null) { // 할인율 관련
            minusAsset = (debitSaveDTO.getMinusAsset() - (debitSaveDTO.getMinusAsset() * debitSaveDTO.getDebitPercent() / 100)); // 할인된 금액 반영해서 넣는것.

            debitSaveDTO.setMinusAsset(minusAsset);
        } else { // 할인액 관련
            minusAsset = (debitSaveDTO.getMinusAsset() - debitSaveDTO.getDebitGet());
            debitSaveDTO.setMinusAsset(minusAsset);
        }

        // 사용되는 계정 정보와 카드 정보 가지고 오기.
        BankEntity bankEntity = br.findById(debitSaveDTO.getBankId()).get();
        CardEntity cardEntity = crr.findById(debitSaveDTO.getCardId()).get();

        // bank에 바로 반영하기.
        String account = debitSaveDTO.getAccount();
        debitSaveDTO.setAccount(account);

        // 전체 내역 추가 하기 - 사용 관련은 기본적으로 마이너스로 추가. -> 카드 내용에 해당 내역 반영
        Double totalAsset = cardEntity.getTotalAsset();
        totalAsset = totalAsset - minusAsset;
        cardEntity.setTotalAsset(totalAsset);
        crr.save(cardEntity);

        // 은행 과년하여 반영하기
        Double bankUseAsset = bankEntity.getTotalAsset();
        bankUseAsset = bankUseAsset - minusAsset;
        bankEntity.setTotalAsset(bankUseAsset);
        // 해당 변경점 업데이트 하기
        br.save(bankEntity);

        // 해당 데이터 저장하기 -> bankName 어 떻게 해결해야 하는지 필요함 (고민 필요) 미완
        DebitEntity debitEntity = DebitEntity.toSaveData(debitSaveDTO, cardEntity, bankEntity);
        Long debitId = dr.save(debitEntity).getId();

        return debitId;
    }

    @Override
    public DebitDetailDTO findById(Long debitId) {
        System.out.println("DebitServiceImpl.findById");

        DebitEntity debitEntity = dr.findById(debitId).get();
        DebitDetailDTO debitDetailDTO = DebitDetailDTO.toMoveData(debitEntity);

        return debitDetailDTO;
    }

    @Override
    public void delete(Long debitId) {
        System.out.println("DebitServiceImpl.delete");

        DebitEntity debitEntity = dr.findById(debitId).get();
        dr.delete(debitEntity);

    }

    @Override
    public Long update(DebitUpdateDTO debitUpdateDTO) {
        System.out.println("DebitServiceImpl.update");
        // 계정 정보 가져 오기
        BankEntity bankEntity = br.findById(debitUpdateDTO.getBankId()).get();
        String bankAccount = bankEntity.getBankName();

        double minusAsset = debitUpdateDTO.getMinusAsset();
        if ((Double)debitUpdateDTO.getDebitGet() == null) { // 할인율 관련
            minusAsset = (debitUpdateDTO.getMinusAsset() - (debitUpdateDTO.getMinusAsset() * debitUpdateDTO.getDebitPercent() / 100)); // 할인된 금액 반영해서 넣는것.

            debitUpdateDTO.setMinusAsset(minusAsset);
        } else { // 할인액 관련
            minusAsset = (debitUpdateDTO.getMinusAsset() - debitUpdateDTO.getDebitGet());
            debitUpdateDTO.setMinusAsset(minusAsset);
        }

        Double totalAsset = bankEntity.getTotalAsset();
        totalAsset = totalAsset - minusAsset;
        bankEntity.setTotalAsset(totalAsset);
        // 반영 내역 집어 넣기. (연동된 계좌에)
        br.save(bankEntity);

        // 카드 관련 하여 사용 내역 반영하기
        CardEntity cardEntity = crr.findById(debitUpdateDTO.getCardId()).get();
        Double cardUseAsset = cardEntity.getTotalAsset();
        cardUseAsset = cardUseAsset - minusAsset;
        cardEntity.setTotalAsset(cardUseAsset);
        // 해당 부분 반영
        crr.save(cardEntity);

        // 체크카드 관련하여 해당 부분 entity에 넣어서 반영하기.
        DebitEntity debitEntity = DebitEntity.toUpdateData(debitUpdateDTO);
        Long debitId = dr.save(debitEntity).getId();

        return debitId;
    }

    @Override
    public List<DebitDetailDTO> findData(Long memberId) {
        MemberEntity memberEntity = mr.findById(memberId).get();
        List<DebitEntity> debitEntityList = memberEntity.getDebitEntityList();
        List<DebitDetailDTO> debitDetailDTOList = new ArrayList<>();

        for (DebitEntity debitEntity:debitEntityList) {
            debitDetailDTOList.add(DebitDetailDTO.toMoveData(debitEntity));
        }

        return debitDetailDTOList;
    }
}
