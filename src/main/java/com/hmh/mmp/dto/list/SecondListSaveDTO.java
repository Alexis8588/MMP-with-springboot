package com.hmh.mmp.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondListSaveDTO {
    private Long flistId;

    private String firstList;
    private String secondList;
}
