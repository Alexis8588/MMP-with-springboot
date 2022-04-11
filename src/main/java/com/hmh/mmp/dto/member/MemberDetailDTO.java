package com.hmh.mmp.dto.member;

import com.hmh.mmp.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailDTO extends MemberSaveDTO {
    private Long memberId;


    public static MemberDetailDTO toMemberDetailDTO(MemberEntity memberEntity) {
        // MemberEntity에 있는 값들을 이용해서 toMemberDetailDTO를 정의한다.
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMemberId(memberEntity.getId());
        memberDetailDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDetailDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDetailDTO.setMemberNickName(memberEntity.getMemberNickName());
        memberDetailDTO.setMemberName(memberEntity.getMemberName());
        memberDetailDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDetailDTO.setMemberMemo(memberEntity.getMemberMemo());
        memberDetailDTO.setMemberPhotoName(memberEntity.getMemberPhotoName());

        return memberDetailDTO;
    }
}
