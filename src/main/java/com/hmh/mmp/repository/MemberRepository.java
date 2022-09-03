package com.hmh.mmp.repository;

import com.hmh.mmp.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByMemberEmail(String memberEmail); // 이메일 중복 검사를 위해서 생성된 메서드
}
