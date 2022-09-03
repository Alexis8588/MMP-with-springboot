package com.hmh.mmp.dto.balance;

import com.hmh.mmp.dto.RecoredYMD;
import com.hmh.mmp.entity.CashEntity;
import com.hmh.mmp.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

// 현금 관련 내역을 보기 위한 것.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceSaveDTO extends RecoredYMD {
    private Long cashId; // 연결된 현금 관련 계좌
    private Long memberId; // 해당 계좌의 로그인 아이디

    private LocalDate date; // 날짜? (기록 날짜)
    private String balanceMemo; //  사용 내역 관련 메모
    private String balanceName; // 사용 내역
    private double plusAsset; // 추가된 금액
    private double minusAsset; // 지출된 금액
    private MultipartFile balancePhoto; // 영수증 관련 사진을 넣어야 하는 것
    private String balancePhotoName;
    private LocalDate calDate;

    private double balanceGet;
    private double balancePercents;

    private double rate;

    private String firstList; // 대분류
    private String secondList; // 중뷴류
    private String thirdList; // 소분류

}
