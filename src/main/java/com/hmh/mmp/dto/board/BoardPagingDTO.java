package com.hmh.mmp.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardPagingDTO extends BoardSaveDTO{
    private Long boardId;

    public BoardPagingDTO(Long id, Integer boardHits, String boardContents, String boardPassword, String boardTitle, String boardWriter, String boardPhotoName, Long id1, String memberNickName) {
    }
}
