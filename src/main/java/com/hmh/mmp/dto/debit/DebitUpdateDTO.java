package com.hmh.mmp.dto.debit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitUpdateDTO extends DebitSaveDTO{
    private Long debitId;
}
