package com.hmh.mmp.entity;

import com.hmh.mmp.dto.board.BoardSaveDTO;
import com.hmh.mmp.dto.board.BoardUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 회원제 게시판 관련을 넣어두기.
@Entity
@Setter
@Getter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String boardWriter;
    private String boardContents;
    private String boardTitle;
    private String boardPassword;
    private Integer boardHits;
    private String boardPhotoName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "boardEntity", fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();


    public static BoardEntity toUpdateData(BoardUpdateDTO boardUpdateDTO) {
        BoardEntity boardEntity = new BoardEntity();
        // memberId와 boardWriter(memberNickName)에 대해서는 save에서 가지고 올것이니까...
        boardEntity.setBoardWriter(boardUpdateDTO.getBoardWriter());
        boardEntity.setBoardContents(boardUpdateDTO.getBoardContents());
        boardEntity.setBoardPassword(boardUpdateDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardUpdateDTO.getBoardTitle());
        boardEntity.setBoardPhotoName(boardUpdateDTO.getBoardPhotoName());
        boardEntity.setId(boardUpdateDTO.getBoardId());

        return boardEntity;
    }

    public static BoardEntity toSaveData(BoardSaveDTO boardSaveDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardSaveDTO.getBoardWriter());
        boardEntity.setMemberEntity(memberEntity);
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardPassword(boardSaveDTO.getBoardPassword());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
        boardEntity.setBoardPhotoName(boardSaveDTO.getBoardPhotoName());
        boardEntity.setBoardHits(boardSaveDTO.getBoardHits());

        return boardEntity;
    }
}
