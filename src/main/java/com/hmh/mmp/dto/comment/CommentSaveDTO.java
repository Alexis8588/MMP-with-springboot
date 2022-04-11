package com.hmh.mmp.dto.comment;

import com.hmh.mmp.entity.BoardEntity;
import com.hmh.mmp.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSaveDTO {
    private String commentWriter;
    private String commentContents;
    private int hate;
    private int like;

    private Long boardId;
    private Long memberId;

    private String firstList; // 대분류
    private String secondList; // 중뷴류
}
