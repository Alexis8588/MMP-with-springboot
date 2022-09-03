package com.hmh.mmp.service;

import com.hmh.mmp.dto.notice.NoticeDetailDTO;
import com.hmh.mmp.dto.notice.NoticePagingDTO;
import com.hmh.mmp.dto.notice.NoticeSaveDTO;
import com.hmh.mmp.dto.notice.NoticeUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface NoticeService {
    List<NoticeDetailDTO> findAll();

    Page<NoticePagingDTO> paging(Pageable pageable);

    NoticeDetailDTO findById(Long noticeId);

    Long save(NoticeSaveDTO noticeSaveDTO) throws IOException;

    Long update(NoticeUpdateDTO noticeUpdateDTO) throws IOException;

    void delete(Long noticeId);
}
