package com.hmh.mmp.controller;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.notice.NoticeDetailDTO;
import com.hmh.mmp.dto.notice.NoticePagingDTO;
import com.hmh.mmp.dto.notice.NoticeSaveDTO;
import com.hmh.mmp.dto.notice.NoticeUpdateDTO;
import com.hmh.mmp.service.NoticeService;
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
import static com.hmh.mmp.common.SessionConst.NICK_NAME;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice/*")
public class NoticeController {
    private NoticeService ns;

    @GetMapping
    public String findAll(Model model, @PageableDefault(page =1)Pageable pageable) {
        System.out.println("NoticeController.findAll");
        List<NoticeDetailDTO> noticeDetailDTO = ns.findAll();
        model.addAttribute("nList", noticeDetailDTO);

        Page<NoticePagingDTO> noticePagingDTOPage = ns.paging(pageable);
        model.addAttribute("noticePage", noticePagingDTOPage);

        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.N_BLOCK_LIMIT))) - 1) * PagingConst.N_BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.N_BLOCK_LIMIT - 1) < noticePagingDTOPage.getTotalPages()) ? startPage + PagingConst.N_BLOCK_LIMIT - 1 : noticePagingDTOPage.getTotalPages();
        model.addAttribute("nStartPage", startPage);
        model.addAttribute("nEndPage", endPage);

        return "notice/findAll";
    }

    @GetMapping("{noticeId}")
    public String findById(Model model, @PathVariable("noticeId") Long noticeId) {
        System.out.println("NoticeController.findById");

        NoticeDetailDTO noticeDetailDTO = ns.findById(noticeId);
        model.addAttribute("nDTO", noticeDetailDTO);

        return "notice/findById";
    }

    @GetMapping("save")
    public String saveForm(Model model, HttpSession session) {
        System.out.println("NoticeController.saveForm");
        Long memberId = (Long)session.getAttribute(LOGIN_ID);
        String memberNickName = (String)session.getAttribute(NICK_NAME);

        NoticeSaveDTO noticeSaveDTO = new NoticeSaveDTO();
        noticeSaveDTO.setMemberId(memberId);
        noticeSaveDTO.setNoticeWriter(memberNickName);

        model.addAttribute("nSave", noticeSaveDTO);

        return "notice/save";
    }

    @PostMapping("save")
    public String save(@ModelAttribute NoticeSaveDTO noticeSaveDTO) throws IOException {
        System.out.println("NoticeController.save");

        Long noticeId = ns.save(noticeSaveDTO);

        return "redirect:/notice/findAll";
    }

    @GetMapping("update/{noticeId}")
    public String updateForm(@PathVariable("noticeId") Long noticeId, Model model) {
        System.out.println("NoticeController.updateForm");

        NoticeDetailDTO noticeDetailDTO = ns.findById(noticeId);
        model.addAttribute("nDTO", noticeDetailDTO);

        return "notice/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute NoticeUpdateDTO noticeUpdateDTO) throws IOException {
        System.out.println("NoticeController.update");

        Long noticeId = ns.update(noticeUpdateDTO);

        return "redirect:/notice/" + noticeId;
    }

    @GetMapping("delete/{noticeId}")
    public String delete(@PathVariable("noticeId") Long noticeId) {
        System.out.println("NoticeController.delete");

        ns.delete(noticeId);

        return "redirect:/notice/";
    }
}
