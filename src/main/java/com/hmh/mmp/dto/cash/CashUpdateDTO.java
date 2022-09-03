package com.hmh.mmp.dto.cash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashUpdateDTO extends CashSaveDTO{
    private Long cashId;
}
