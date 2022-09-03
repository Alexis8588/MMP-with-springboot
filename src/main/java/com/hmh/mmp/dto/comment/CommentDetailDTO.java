package com.hmh.mmp.dto.comment;

import com.hmh.mmp.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetailDTO extends CommentSaveDTO{
    private Long commentId;

    public static CommentDetailDTO toMoveData(CommentEntity commentEntity) {
        CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
        commentDetailDTO.setCommentId(commentEntity.getId());
        commentDetailDTO.setCommentContents(commentEntity.getCommentContents());
        commentDetailDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDetailDTO.setBoardId(commentEntity.getBoardEntity().getId());
        commentDetailDTO.setMemberId(commentEntity.getMemberEntity().getId());

        return commentDetailDTO;
    }
}
