package com.hmh.mmp.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeSaveDTO {
    private Long memberId;

    private String noticeWriter;
    private String noticeContents;
    private String noticeTitle;
    private String noticePassword;
    private Integer noticeHits;
    private MultipartFile noticePhoto;
    private String noticePhotoName;
}
