package com.hmh.mmp.dto.cash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashPagingDTO extends CashSaveDTO{
    private Long cashId;

    public CashPagingDTO(Long id, String cashMemo, String cashName, String cashPhotoName, Long totalAsset, LocalDateTime nowTime) {
    }
}
