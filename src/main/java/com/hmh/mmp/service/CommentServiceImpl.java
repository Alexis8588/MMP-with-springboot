package com.hmh.mmp.service;

import com.hmh.mmp.dto.comment.CommentDetailDTO;
import com.hmh.mmp.dto.comment.CommentSaveDTO;
import com.hmh.mmp.entity.BoardEntity;
import com.hmh.mmp.entity.CommentEntity;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.repository.BoardRepository;
import com.hmh.mmp.repository.CommentRepository;
import com.hmh.mmp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository cr;
    private final BoardRepository br;
    private final MemberRepository mr;

    @Override
    public Long save(CommentSaveDTO commentSaveDTO) {
        MemberEntity memberEntity = mr.findById(commentSaveDTO.getMemberId()).get();
        BoardEntity boardEntity = br.findById(commentSaveDTO.getBoardId()).get();

        CommentEntity commentEntity = CommentEntity.toSaveData(commentSaveDTO, boardEntity, memberEntity);
        Long commentId = cr.save(commentEntity).getId();

        return commentId;
    }

    @Override
    public List<CommentDetailDTO> findAll(Long boardId) {
        Optional<BoardEntity> boardEntityOptional = br.findById(boardId);
        BoardEntity boardEntity = boardEntityOptional.get();

        List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();

        // List의 내용을 Entity에서 Detail에 담아서 보내야 함.
        List<CommentDetailDTO> commentList = new ArrayList<>();

        for (CommentEntity c: commentEntityList) {
            CommentDetailDTO commentDetailDTO = CommentDetailDTO.toMoveData(c);
            commentList.add(commentDetailDTO);
        }

        return commentList;
    }
}
