package com.hmh.mmp.dto.credit;

import com.hmh.mmp.entity.CreditEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDetailDTO extends CreditSaveDTO{
    private Long creditId;

    public static CreditDetailDTO toMoveData(CreditEntity creditEntity) {
        CreditDetailDTO creditDetailDTO = new CreditDetailDTO();
        creditDetailDTO.setCreditId(creditEntity.getId());
        creditDetailDTO.setCreditGet(creditEntity.getCreditGet());
        creditDetailDTO.setCreditMemo(creditEntity.getCreditMemo());
        creditDetailDTO.setCreditName(creditEntity.getCreditName());
        creditDetailDTO.setCreditPhotoName(creditEntity.getCreditPhotoName());
//        creditDetailDTO.setBankId(creditEntity.);
        creditDetailDTO.setCardId(creditEntity.getCardEntity().getId());
        creditDetailDTO.setDate(creditEntity.getDate());
        creditDetailDTO.setMinusAsset(creditEntity.getMinusAsset());
        creditDetailDTO.setMonth(creditDetailDTO.getMonth());
        creditDetailDTO.setRate(creditEntity.getRate());

        return creditDetailDTO;
    }
}
