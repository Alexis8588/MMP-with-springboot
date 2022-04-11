package com.hmh.mmp.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardThirdListSaveDTO {
    private Long cflistId;
    private Long cslistId;

    private String firstList;
    private String secondList;
    private String thirdList;
}
