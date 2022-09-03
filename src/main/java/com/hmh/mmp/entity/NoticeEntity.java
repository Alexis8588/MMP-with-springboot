package com.hmh.mmp.entity;

import com.hmh.mmp.dto.notice.NoticeSaveDTO;
import com.hmh.mmp.dto.notice.NoticeUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "notice_table")
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String noticeWriter;
    private String noticeContents;
    private String noticeTitle;
    private String noticePassword;
    private Integer noticeHits;
    private String noticePhotoName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "noticeEntity")
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static NoticeEntity toSaveData(NoticeSaveDTO noticeSaveDTO, MemberEntity memberEntity) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setNoticeTitle(noticeSaveDTO.getNoticeTitle());
        noticeEntity.setNoticePassword(noticeSaveDTO.getNoticePassword());
        noticeEntity.setNoticeWriter(noticeSaveDTO.getNoticeWriter());
        noticeEntity.setNoticeContents(noticeSaveDTO.getNoticeContents());
        noticeEntity.setNoticePhotoName(noticeSaveDTO.getNoticePhotoName());
        noticeEntity.setMemberEntity(memberEntity);

        return noticeEntity;
    }

    public static NoticeEntity toUpdateData(NoticeUpdateDTO noticeUpdateDTO) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setNoticeTitle(noticeUpdateDTO.getNoticeTitle());
        noticeEntity.setNoticeContents(noticeUpdateDTO.getNoticeContents());
        noticeEntity.setNoticePassword(noticeUpdateDTO.getNoticePassword());
        noticeEntity.setNoticeWriter(noticeUpdateDTO.getNoticeWriter());
        noticeEntity.setNoticePhotoName(noticeUpdateDTO.getNoticePhotoName());

        return noticeEntity;
    }
}
