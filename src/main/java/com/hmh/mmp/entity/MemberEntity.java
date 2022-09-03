package com.hmh.mmp.entity;

import com.hmh.mmp.dto.member.MemberDetailDTO;
import com.hmh.mmp.dto.member.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="member_table")
public class MemberEntity extends BaseEntity {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increments)
    @Column(name="member_id")
    private Long id; // 분류 번호
    @Column(length = 50, unique = true)
    private String memberEmail; // 가입 아이디
    @Column(length = 30)
    private String memberPassword; // 가입비밀번호
    @Column(length = 30)
    private String memberName; // 회원 이름
    @Column(length = 30)
    private String memberNickName; // 회원 닉네임
    @Column(length = 20)
    private String memberPhone; // 전화번호
    @Column(length = 1000)
    private String memberMemo; // 메모
    @Column
    private String memberPhotoName; // 사진 보관을 위한 것
    private String memberAddress; // 숍시 주소등록을 위함?
    private Integer memberLevel; // 등급 분류를 위함.
    private String memberStep;


    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
    private List<BankEntity> bankEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
    private List<AccountEntity> accountEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
    private List<CashEntity> cashEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
    private List<BalanceEntity> balanceEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
    private List<CardEntity> cardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
    private List<CreditEntity> creditEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
    private List<DebitEntity> debitEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
    private List<NoticeEntity> noticeEntityList = new ArrayList<>();

    // 하단 부의 내용 재설정 필요s
    public static MemberEntity saveMember(MemberSaveDTO msDTO) {
        MemberEntity mEntity = new MemberEntity();

        mEntity.setMemberEmail(msDTO.getMemberEmail());
        mEntity.setMemberPassword(msDTO.getMemberPassword());
        mEntity.setMemberName(msDTO.getMemberName());
        mEntity.setMemberNickName(msDTO.getMemberNickName());
        mEntity.setMemberPhone(msDTO.getMemberPhone());
        mEntity.setMemberMemo(msDTO.getMemberMemo());
        mEntity.setMemberPhotoName(msDTO.getMemberPhotoName());

        return mEntity;
    }

    public static MemberEntity updateMember(MemberDetailDTO memberDetailDTO) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMemberEmail(memberDetailDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDetailDTO.getMemberPassword());
        memberEntity.setMemberName(memberDetailDTO.getMemberName());
        memberEntity.setMemberNickName(memberDetailDTO.getMemberNickName());
        memberEntity.setMemberPhone(memberDetailDTO.getMemberPhone());
        memberEntity.setMemberMemo(memberDetailDTO.getMemberMemo());
        memberEntity.setMemberPhotoName(memberDetailDTO.getMemberPhotoName());

        return memberEntity;
    }
}
