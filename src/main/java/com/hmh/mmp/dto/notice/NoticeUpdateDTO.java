package com.hmh.mmp.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUpdateDTO extends NoticeSaveDTO{
    private Long noticeId;
}
