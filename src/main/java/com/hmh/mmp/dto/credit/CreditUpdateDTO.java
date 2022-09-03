package com.hmh.mmp.dto.credit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditUpdateDTO extends CreditSaveDTO{
    private Long creditId;
}
