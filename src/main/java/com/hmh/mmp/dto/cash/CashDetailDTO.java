package com.hmh.mmp.dto.cash;

import com.hmh.mmp.entity.CashEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashDetailDTO extends CashSaveDTO{
    private Long cashId;

    public static CashDetailDTO toMoveData(CashEntity cashEntity) {
        CashDetailDTO cashDetailDTO = new CashDetailDTO();

        cashDetailDTO.setCashId(cashEntity.getId());
        cashDetailDTO.setCashName(cashEntity.getCashName());
        cashDetailDTO.setCashMemo(cashEntity.getCashMemo());
        cashDetailDTO.setCashPhotoName(cashEntity.getCashPhotoName());
        cashDetailDTO.setTotalAsset(cashEntity.getTotalAsset());
        cashDetailDTO.setMemberId(cashEntity.getMemberEntity().getId());

        return cashDetailDTO;
    }
}
