package com.hmh.mmp.dto.member;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPagingDTO extends MemberSaveDTO {
    private Long memberId;

    public MemberPagingDTO(Long id, String memberEmail, String memberPassword, String memberName, String memberNickName, String memberPhone, String memberMemo, String memberPhotoName) {
    }
}
