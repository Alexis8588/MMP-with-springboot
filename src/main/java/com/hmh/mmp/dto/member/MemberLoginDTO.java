package com.hmh.mmp.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginDTO {
    private String memberEmail;
    private String memberPassword;
    private String memberNickName;
    private Long id;
}
