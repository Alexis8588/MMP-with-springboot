package com.hmh.mmp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecoredYMD { // 날짜 선택할 경우 어떻게 할 것인지에 대한 기록 방법
    // 1번쨰 안
    private int year;
    private int month;
    private int day;

}
