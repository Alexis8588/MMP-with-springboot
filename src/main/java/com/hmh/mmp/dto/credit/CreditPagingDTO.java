package com.hmh.mmp.dto.credit;

import com.hmh.mmp.entity.BankEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditPagingDTO extends CreditSaveDTO {
    private Long creditId;

    public CreditPagingDTO(Long id, Double creditGet, String creditMemo, String creditName, String creditPhotoName, BankEntity bankEntity, Long minusAsset, LocalDate date) {
    }

    public CreditPagingDTO(Long id, Double creditGet, String creditMemo, String creditName, String creditPhotoName, BankEntity bankEntity, Long minusAsset, LocalDate date, Integer month, Double rate) {
    }

    public CreditPagingDTO(Long id, Double creditGet, String creditMemo, String creditName, String creditPhotoName, String account, Long minusAsset, Integer month, Double rate) {
    }
}
