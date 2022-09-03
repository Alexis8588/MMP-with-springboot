package com.hmh.mmp.dto.card;

import com.hmh.mmp.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardSaveDTO {
    private String cardName;
    private String cardMemo;
    private int level;
    private String cardTag;
    private MultipartFile cardPhoto;
    private String cardPhotoName;
    private double totalAsset;

    private Long memberId;
}
