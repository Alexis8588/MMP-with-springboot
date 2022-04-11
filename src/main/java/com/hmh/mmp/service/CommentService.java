package com.hmh.mmp.service;

import com.hmh.mmp.dto.comment.CommentDetailDTO;
import com.hmh.mmp.dto.comment.CommentSaveDTO;

import java.util.List;

public interface CommentService {
    Long save(CommentSaveDTO commentSaveDTO);

    List<CommentDetailDTO> findAll(Long boardId);
}
