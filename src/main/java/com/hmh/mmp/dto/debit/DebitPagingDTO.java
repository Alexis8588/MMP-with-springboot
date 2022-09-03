package com.hmh.mmp.dto.debit;

import lombok.Data;

@Data
public class DebitPagingDTO extends DebitSaveDTO {
    private Long debitId;

    public DebitPagingDTO(Long id, Double debitGet, String debitMemo, String debitName, Double minusAsset, String account, String debitPhotoName) {
    }
}
