package com.hmh.mmp.dto.credit;

import com.hmh.mmp.dto.RecoredYMD;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditSaveDTO extends RecoredYMD {
    private Long cardId;
    private Long bankId;
    private Long memberId;

    private LocalDate date;
    private String creditName; // 내역
    private MultipartFile creditPhoto;
    private String creditPhotoName; // 사진명
    private String creditMemo;
    private double minusAsset; // 지출
    private double rate; // 할부이자.
    private int month; // 할부

    private double creditGet; // 캐쉬백
    private double creditPercents;

    private String firstList; // 대분류
    private String secondList; // 중뷴류
    private String thirdList; // 소분류

    private String account;
}
