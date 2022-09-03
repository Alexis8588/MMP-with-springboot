package com.hmh.mmp.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateDTO extends BoardSaveDTO{
    private Long boardId;
}
