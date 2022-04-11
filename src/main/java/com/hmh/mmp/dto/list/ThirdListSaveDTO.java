package com.hmh.mmp.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdListSaveDTO {
    private Long flistId;
    private Long slistId;

    private String firstList;
    private String secondList;
    private String thirdList;
}
