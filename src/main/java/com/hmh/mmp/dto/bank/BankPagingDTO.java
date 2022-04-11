package com.hmh.mmp.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankPagingDTO extends BankSaveDTO {
    private Long bankId;

    public BankPagingDTO(Long id, Long id1, String bankName, String bankMemo, Long totalAsset) {
    }
}
