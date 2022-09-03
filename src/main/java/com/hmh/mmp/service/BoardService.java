package com.hmh.mmp.service;

import com.hmh.mmp.dto.board.BoardDetailDTO;
import com.hmh.mmp.dto.board.BoardPagingDTO;
import com.hmh.mmp.dto.board.BoardSaveDTO;
import com.hmh.mmp.dto.board.BoardUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    List<BoardDetailDTO> findAll();

    Page<BoardPagingDTO> paging(Pageable pageable);

    BoardDetailDTO findById(Long boardId);

    Long update(BoardUpdateDTO boardUpdateDTO) throws IOException;

    Long save(BoardSaveDTO boardSaveDTO) throws IOException;

    void delete(Long boardId);
}
