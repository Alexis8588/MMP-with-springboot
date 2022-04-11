package com.hmh.mmp.dto.bank;

import com.hmh.mmp.entity.BankEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class BankDetailDTO extends BankSaveDTO {
    private Long bankId;

    public static BankDetailDTO toBankDetail(BankEntity bankEntity) {
        BankDetailDTO bankDetailDTO = new BankDetailDTO();
        bankDetailDTO.setBankId(bankEntity.getId());
        bankDetailDTO.setBankName(bankEntity.getBankName());
        bankDetailDTO.setBankMemo(bankEntity.getBankMemo());
        bankDetailDTO.setTotalAsset(bankEntity.getTotalAsset());
        // ?? 이걸 어떻게 해야하나.
        bankDetailDTO.setMemberId(bankEntity.getMemberEntity().getId());
        bankDetailDTO.setBankPhotoName(bankEntity.getBankPhotoName());

        return bankDetailDTO;
    }
}
