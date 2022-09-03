package com.hmh.mmp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainDetailDTO {
    private Long bankId;
    private Long bankId2;

    private Long cashId;
    private Long cashId2;

    private Long cardId;
    private Long cardId2;

    private double asset;

    private String bankName;
    private String bankName2;

    private String cashName;
    private String cashName2;
}
