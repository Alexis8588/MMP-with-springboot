package com.hmh.mmp.dto.cash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashSaveDTO {
    @NotBlank
    @Column(length = 50)
    private String cashName;
    private Long memberId;
    private String cashMemo;
    private double totalAsset;
    private String cashPhotoName;
    private MultipartFile cashPhoto;
}
