package com.hmh.mmp.controller;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.board.BoardDetailDTO;
import com.hmh.mmp.dto.board.BoardPagingDTO;
import com.hmh.mmp.dto.board.BoardSaveDTO;
import com.hmh.mmp.dto.board.BoardUpdateDTO;
import com.hmh.mmp.dto.member.MemberDetailDTO;
import com.hmh.mmp.service.BoardService;
import com.hmh.mmp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.hmh.mmp.common.SessionConst.LOGIN_ID;

@Controller
@RequestMapping("/board/")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService bos;
    private final MemberService ms;

    @GetMapping
    public String findAll(Model model, @PageableDefault(page = 1)Pageable pageable) {
        System.out.println("BoardController.findAll");
        
        // 전체 내용 불러오기
        List<BoardDetailDTO> boardDetailDTOList = bos.findAll();
        model.addAttribute("bdList", boardDetailDTOList);

        // 페이징 처리 필요
        Page<BoardPagingDTO> boardPage = bos.paging(pageable);
        model.addAttribute("boardPage", boardPage);

        int startPage = (((int) (Math.ceil((double)pageable.getPageNumber() / PagingConst.BO_BLOCK_LIMIT))) - 1) * PagingConst.BO_BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BO_BLOCK_LIMIT - 1) < boardPage.getTotalPages()) ?  startPage + PagingConst.BO_BLOCK_LIMIT - 1 : boardPage.getTotalPages();
        model.addAttribute("bStartPage", startPage);
        model.addAttribute("bEndPage", endPage);
        
        return "board/findAll";
    }

    @GetMapping("${boardId}")
    public String findById(Model model, @PathVariable("boardId") Long boardId) {
        System.out.println("BoardController.findById");
        BoardDetailDTO boardDetailDTO = bos.findById(boardId);
        model.addAttribute("bDTO", boardDetailDTO);

        return "board/findById";
    }

    @GetMapping("/update/${boardId}")
    public String updateForm(Model model, @PathVariable("boardId") Long boardId) {
        System.out.println("BoardController.updateForm");
        BoardDetailDTO boardDetailDTO = bos.findById(boardId);
        model.addAttribute("bDTO", boardDetailDTO);

        return "board/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardUpdateDTO boardUpdateDTO) throws IOException {
        Long boardId = bos.update(boardUpdateDTO);

        return "redirect:board/findAll";
    }

    @GetMapping("save")
    public String saveForm(Model model, HttpSession session) {
        Long memberId = (Long)session.getAttribute(LOGIN_ID);
        MemberDetailDTO memberDetailDTO = ms.findById(memberId);

        BoardSaveDTO boardSaveDTO = BoardSaveDTO.toMoveDate(memberDetailDTO);

        model.addAttribute("bDTO", boardSaveDTO);

        return "board/save";
    }

    @PostMapping("save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO) throws IOException {
        Long boardId = bos.save(boardSaveDTO);

        return "redirect:/board/findAll";
    }

    @GetMapping("delete")
    @DeleteMapping("delete")
    public String delete(@PathVariable("boardId") Long boardId) {
        System.out.println("BoardController.delete");
        bos.delete(boardId);

        return "redirect:/board/";
    }


}
