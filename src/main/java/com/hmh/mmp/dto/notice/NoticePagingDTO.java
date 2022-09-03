package com.hmh.mmp.dto.notice;

import lombok.Data;

@Data
public class NoticePagingDTO extends NoticeSaveDTO{
    private Long noticeId;

    public NoticePagingDTO(Long id, Integer noticeHits, String noticeContents, String noticePassword, String noticeTitle, String noticeWriter, String noticePhotoName, Long id1) {
    }
}
