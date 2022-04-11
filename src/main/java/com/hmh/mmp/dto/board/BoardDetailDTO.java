package com.hmh.mmp.dto.board;

import com.hmh.mmp.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDTO extends BoardSaveDTO{
    private Long boardId;

    public static BoardDetailDTO toMoveData(BoardEntity boardEntity) {
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        boardDetailDTO.setBoardId(boardEntity.getId());
        boardDetailDTO.setBoardHits(boardEntity.getBoardHits());
        boardDetailDTO.setBoardContents(boardEntity.getBoardContents());
        boardDetailDTO.setBoardPassword(boardEntity.getBoardPassword());
        boardDetailDTO.setBoardPhotoName(boardEntity.getBoardPhotoName());
        boardDetailDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDetailDTO.setBoardWriter(boardEntity.getBoardWriter());

        return boardDetailDTO;
    }
}
