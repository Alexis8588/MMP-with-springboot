package com.hmh.mmp.dto.notice;

import com.hmh.mmp.entity.NoticeEntity;
import lombok.Data;

@Data
public class NoticeDetailDTO extends NoticeSaveDTO{
    private Long noticeId;

    public static NoticeDetailDTO toDataMove(NoticeEntity noticeEntity) {
        NoticeDetailDTO noticeDetailDTO = new NoticeDetailDTO();
        noticeDetailDTO.setNoticeId(noticeEntity.getId());
        noticeDetailDTO.setNoticeContents(noticeEntity.getNoticeContents());
        noticeDetailDTO.setNoticeHits(noticeEntity.getNoticeHits());
        noticeDetailDTO.setNoticeTitle(noticeEntity.getNoticeTitle());
        noticeDetailDTO.setNoticePassword(noticeEntity.getNoticePassword());
        noticeDetailDTO.setMemberId(noticeEntity.getMemberEntity().getId());
        noticeDetailDTO.setNoticePhotoName(noticeEntity.getNoticePhotoName());
        noticeDetailDTO.setNoticeWriter(noticeEntity.getNoticeWriter());

        return noticeDetailDTO;
    }
}
