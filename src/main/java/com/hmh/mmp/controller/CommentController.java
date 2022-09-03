package com.hmh.mmp.controller;

import com.hmh.mmp.dto.comment.CommentDetailDTO;
import com.hmh.mmp.dto.comment.CommentSaveDTO;
import com.hmh.mmp.service.BoardService;
import com.hmh.mmp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.hmh.mmp.common.SessionConst.LOGIN_ID;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService cs;
    private final BoardService bs;

    @PostMapping("save")
    public @ResponseBody List<CommentDetailDTO> save(@ModelAttribute CommentSaveDTO commentSaveDTO, HttpSession session, Model model, @PathVariable("boardId") Long boardId, @PathVariable("noticeId") Long noticeId) {
        System.out.println("CommentController.save");
        // board 관련 정보를 어떻게 처리 해야하는가?

        // member는?
        Long memberId = (Long)session.getAttribute(LOGIN_ID);
        commentSaveDTO.setMemberId(memberId);

        // notice 부분을 어떻게 처리해야 하지.? 그냥 코멘트를 넣지 말까?
        Long commentId = cs.save(commentSaveDTO);

        List<CommentDetailDTO> commentDetailDTOList = cs.findAll(commentSaveDTO.getBoardId());
        model.addAttribute("cList", commentDetailDTOList);

        return commentDetailDTOList;
    }

}
