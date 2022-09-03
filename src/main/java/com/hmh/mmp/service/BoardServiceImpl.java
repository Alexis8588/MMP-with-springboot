package com.hmh.mmp.service;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.board.BoardDetailDTO;
import com.hmh.mmp.dto.board.BoardPagingDTO;
import com.hmh.mmp.dto.board.BoardSaveDTO;
import com.hmh.mmp.dto.board.BoardUpdateDTO;
import com.hmh.mmp.entity.BoardEntity;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.repository.BoardRepository;
import com.hmh.mmp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository bor;
    private final MemberRepository mr;

    @Override
    public List<BoardDetailDTO> findAll() {
        List<BoardEntity> boardEntityList = bor.findAll();

        List<BoardDetailDTO> boardDetailDTOList = new ArrayList<>();
        for (BoardEntity be:boardEntityList) {
            boardDetailDTOList.add(BoardDetailDTO.toMoveData(be));
        }

        return boardDetailDTOList;
    }

    @Override
    public Page<BoardPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();

        page = (page == 1) ? 0 : (page - 1);
        Page<BoardEntity> boardEntityList = bor.findAll(PageRequest.of(page, PagingConst.BO_PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardPagingDTO> bList =  boardEntityList.map(
                board -> new BoardPagingDTO(board.getId(),
                        board.getBoardHits(),
                        board.getBoardContents(),
                        board.getBoardPassword(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardPhotoName(),
                        board.getMemberEntity().getId(),
                        board.getMemberEntity().getMemberNickName())
        );

        return bList;
    }

    @Override
    @Transactional
    public BoardDetailDTO findById(Long boardId) {
        System.out.println("BoardServiceImpl.findById");
        // 데이터를 가지고 오고
        BoardEntity boardEntity = bor.findById(boardId).get();
//        데이터를 보내고
        BoardDetailDTO boardDetailDTO = BoardDetailDTO.toMoveData(boardEntity);
        // 조회수 저장 관련 코드 필요함.
//        bor.hits?;

        // Hit 관련 건
//        int hits = boardEntity.getBoardHits();
//        hits = hits + 1;
//        boardEntity.setBoardHits(hits);

        int hits = bor.boardHits(boardId);
        boardEntity.setBoardHits(hits);

        return boardDetailDTO;
    }

    @Override
    public Long update(BoardUpdateDTO boardUpdateDTO) throws IOException {
        MultipartFile boardPhoto = boardUpdateDTO.getBoardPhoto();
        String boardPhotoName = boardPhoto.getOriginalFilename();
        boardPhotoName = System.currentTimeMillis() + "-" + boardPhotoName;

        String savePath = "D:\\GitHub\\MoneyManager_for_intel\\src\\main\\resources\\photo\\board" + boardPhotoName;

        if (!boardPhoto.isEmpty()) {
            boardPhoto.transferTo(new File(savePath));
            boardUpdateDTO.setBoardPhotoName(boardPhotoName);
        }
        
        // 데이터 저장
        BoardEntity boardEntity = BoardEntity.toUpdateData(boardUpdateDTO);
        Long boardId = bor.save(boardEntity).getId();
        return boardId;
    }

    @Override
    public Long save(BoardSaveDTO boardSaveDTO) throws IOException {
        MultipartFile boardPhoto = boardSaveDTO.getBoardPhoto();
        String boardPhotoName = boardPhoto.getOriginalFilename();
        boardPhotoName = System.currentTimeMillis() + "-" + boardPhotoName;

        String savePath = "D:\\GitHub\\MoneyManager_for_intel\\src\\main\\resources\\photo\\board" + boardPhotoName;

        if (!boardPhoto.isEmpty()) {
            boardPhoto.transferTo(new File(savePath));
            boardSaveDTO.setBoardPhotoName(boardPhotoName);
        }

        // 조회수 관련 언급
        int hits = boardSaveDTO.getBoardHits();
        hits = 0;
        boardSaveDTO.setBoardHits(hits);

        //
        MemberEntity memberEntity = mr.findById(boardSaveDTO.getMemberId()).get();

        // 데이터 저장
        BoardEntity boardEntity = BoardEntity.toSaveData(boardSaveDTO, memberEntity);
        Long boardId = bor.save(boardEntity).getId();

        return boardId;
    }

    @Override
    public void delete(Long boardId) {
        BoardEntity boardEntity = bor.findById(boardId).get();
        bor.delete(boardEntity);
    }
}
