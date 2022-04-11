package com.hmh.mmp.dto.balance;

import com.hmh.mmp.entity.BalanceEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDetailDTO extends BalanceSaveDTO {
    private Long balanceId;

    private Long cashId; // 연결된 현금 관련 계좌
    private Long memberId; // 해당 계좌의 로그인 아이디

    private LocalDate date; // 날짜? (기록 날짜)
    private String balanceMemo; //  사용 내역 관련 메모
    private String balanceName; // 사용 내역
    private long plusAsset; // 추가된 금액
    private long minusAsset; // 지출된 금액
    private MultipartFile balancePhoto; // 영수증 관련 사진을 넣어야 하는 것
    private String balancePhotoName;
    private LocalDate calDate;

    private Double balanceGet;
    private Double balancePercents;

    private double rate;

    private String firstList; // 대분류
    private String secondList; // 중뷴류
    private String thirdList; // 소분류

    public static BalanceDetailDTO toMoveData(BalanceEntity balanceEntity) {
        BalanceDetailDTO balanceDetailDTO = new BalanceDetailDTO();
        balanceDetailDTO.setBalanceId(balanceEntity.getId());
        balanceDetailDTO.setBalanceMemo(balanceEntity.getBalanceMemo());
        balanceDetailDTO.setBalanceName(balanceEntity.getBalanceName());
        balanceDetailDTO.setCashId(balanceEntity.getCashEntity().getId());
        balanceDetailDTO.setBalancePhotoName(balanceEntity.getBalancePhotoName());
        balanceDetailDTO.setMinusAsset(balanceEntity.getMinusAsset());
        balanceDetailDTO.setPlusAsset(balanceEntity.getPlusAsset());

        return balanceDetailDTO;
    }
}
