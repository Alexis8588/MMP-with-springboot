package com.hmh.mmp.dto.debit;

import com.hmh.mmp.entity.DebitEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitDetailDTO extends DebitSaveDTO{
    private Long debitId;

    public static DebitDetailDTO toMoveData(DebitEntity debitEntity) {
        DebitDetailDTO debitDetailDTO = new DebitDetailDTO();
        debitDetailDTO.setDebitId(debitEntity.getId());
        debitDetailDTO.setDebitGet(debitEntity.getDebitGet());
        debitDetailDTO.setDebitMemo(debitEntity.getDebitMemo());
        debitDetailDTO.setDebitName(debitEntity.getDebitName());
        debitDetailDTO.setCardId(debitEntity.getCardEntity().getId());
//        debitDetailDTO.setBankId(debitEntity.getBankEntity().getId());
        debitDetailDTO.setDate(debitEntity.getDate());
        debitDetailDTO.setMinusAsset(debitEntity.getMinusAsset());
        debitDetailDTO.setDebitPhotoName(debitEntity.getDebitPhotoName());

        return debitDetailDTO;
    }
}
