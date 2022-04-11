package com.hmh.mmp.dto.balance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalancePagingDTO extends BalanceSaveDTO {
    private Long balanceId;

    public BalancePagingDTO(Long id, String balanceName, String balanceMemo, String balancePhotoName, long minusAsset, long plusAsset, LocalDateTime nowTime) {
    }
}
