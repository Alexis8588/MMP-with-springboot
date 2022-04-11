package com.hmh.mmp.service;

import com.hmh.mmp.dto.card.CardDetailDTO;
import com.hmh.mmp.dto.card.CardPagingDTO;
import com.hmh.mmp.dto.card.CardSaveDTO;
import com.hmh.mmp.dto.card.CardUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardService {
    List<CardDetailDTO> findAll(Long memberId);

    Page<CardPagingDTO> paging(Pageable pageable);

    Long save(CardSaveDTO cardSaveDTO);

    CardDetailDTO findById(Long cardId);

    Long update(CardUpdateDTO cardUpdateDTO);

    void delete(Long cardId);
}
