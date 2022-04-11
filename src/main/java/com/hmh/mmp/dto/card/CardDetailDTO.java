package com.hmh.mmp.dto.card;

import com.hmh.mmp.entity.CardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailDTO extends CardSaveDTO{
    private Long cardId;

    public static CardDetailDTO toMoveData(CardEntity cardEntity) {
        CardDetailDTO cardDetailDTO = new CardDetailDTO();
        cardDetailDTO.setCardId(cardEntity.getId());
        cardDetailDTO.setCardMemo(cardEntity.getCardMemo());
        cardDetailDTO.setCardName(cardEntity.getCardName());
        cardDetailDTO.setTotalAsset(cardEntity.getTotalAsset());
        cardDetailDTO.setCardPhotoName(cardEntity.getCardPhotoName());
        cardDetailDTO.setMemberId(cardEntity.getMemberEntity().getId());

        return cardDetailDTO;
    }
}
