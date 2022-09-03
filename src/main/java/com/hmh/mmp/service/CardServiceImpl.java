package com.hmh.mmp.service;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.card.CardDetailDTO;
import com.hmh.mmp.dto.card.CardPagingDTO;
import com.hmh.mmp.dto.card.CardSaveDTO;
import com.hmh.mmp.dto.card.CardUpdateDTO;
import com.hmh.mmp.entity.CardEntity;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.repository.CardRepository;
import com.hmh.mmp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{
    private final CardRepository crr;
    private final MemberRepository mr;

    @Override
    public List<CardDetailDTO> findAll(Long memberId) {
        List<CardEntity> cardEntities = crr.findAll(memberId);
        List<CardDetailDTO> cardDetailDTOList = new ArrayList<>();

        for (CardEntity ce: cardEntities) {
            cardDetailDTOList.add(CardDetailDTO.toMoveData(ce));
        }

        return cardDetailDTOList;
    }

    @Override
    public Page<CardPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1) ? 0 : (page - 1);
        Page<CardEntity> cardEntities = crr.findAll(PageRequest.of(page, PagingConst.CR_PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

        Page<CardPagingDTO> cardPage = cardEntities.map(
                card -> new CardPagingDTO(card.getId(),
                        card.getCardMemo(),
                        card.getCardName(),
                        card.getTotalAsset(),
                        card.getCardPhotoName(),
                        card.getNowTime(),
                        card.getStartTime(),
                        card.getCardTag())
        );

        return cardPage;
    }

    @Override
    public Long save(CardSaveDTO cardSaveDTO) {
        System.out.println("CardServiceImpl.save");
        MemberEntity memberEntity = mr.findById(cardSaveDTO.getMemberId()).get();

        Integer cardTag = cardSaveDTO.getLevel();
        if (cardTag == 0) {
            cardSaveDTO.setCardTag("체크카드");
        } else {
            cardSaveDTO.setCardTag("신용카드");
        }

        CardEntity cardEntity = CardEntity.toSaveData(cardSaveDTO, memberEntity);
        Long cardId = crr.save(cardEntity).getId();
        return cardId;
    }

    @Override
    public CardDetailDTO findById(Long cardId) {
        System.out.println("CardServiceImpl.findById");

        CardEntity cardEntity = crr.findById(cardId).get();
        CardDetailDTO cardDetailDTO = CardDetailDTO.toMoveData(cardEntity);

        return cardDetailDTO;
    }

    @Override
    public Long update(CardUpdateDTO cardUpdateDTO) {
        CardEntity cardEntity = CardEntity.toUpdateData(cardUpdateDTO);
        Long cardId = crr.save(cardEntity).getId();
        // 이거로 끝인가? 아니면 해당 가계부 금액 차액만큼을 기록하는 것을 만들어야 하는가.

        return cardId;
    }

    @Override
    public void delete(Long cardId) {
        CardEntity cardEntity = crr.findById(cardId).get();
        crr.delete(cardEntity);
    }
}
