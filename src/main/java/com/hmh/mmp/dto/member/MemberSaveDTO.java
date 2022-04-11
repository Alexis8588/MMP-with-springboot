package com.hmh.mmp.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveDTO {
    @NotBlank(message="이메일은 필수입니다.")
    private String memberEmail;
    @NotBlank
    @Length(min=4, max=16, message="4~16자리로 입력하세요")
    private String memberPassword;
    @NotBlank(message="이름은 필수 입니다.")
    private String memberName;
    private String memberNickName;
    private String memberPhone;
    private String memberMemo;
    private MultipartFile memberPhoto;
    private String memberPhotoName;
    private String memberAddress; // 숍시 주소등록을 위함?
    private int memberLevel;
    private String memberStep;
}
