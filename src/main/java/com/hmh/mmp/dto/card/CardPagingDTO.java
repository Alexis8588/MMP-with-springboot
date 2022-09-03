package com.hmh.mmp.dto.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardPagingDTO extends CardSaveDTO{
    private Long cardId;

    public CardPagingDTO(Long id, String cardMemo, String cardName, Long totalAsset, String cardPhotoName, LocalDateTime nowTime, LocalDateTime startTime) {
    }

    public CardPagingDTO(Long id, String cardMemo, String cardName, Long totalAsset, String cardPhotoName, LocalDateTime nowTime, LocalDateTime startTime, String cardTag) {
    }
}
