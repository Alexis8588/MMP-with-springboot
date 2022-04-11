package com.hmh.mmp.controller;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.card.CardDetailDTO;
import com.hmh.mmp.dto.card.CardPagingDTO;
import com.hmh.mmp.dto.card.CardSaveDTO;
import com.hmh.mmp.dto.card.CardUpdateDTO;
import com.hmh.mmp.dto.credit.CreditDetailDTO;
import com.hmh.mmp.dto.credit.CreditPagingDTO;
import com.hmh.mmp.dto.debit.DebitDetailDTO;
import com.hmh.mmp.dto.debit.DebitPagingDTO;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.service.CardService;
import com.hmh.mmp.service.CreditService;
import com.hmh.mmp.service.DebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.hmh.mmp.common.SessionConst.LOGIN_ID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/card/*")
public class CardController {
    private final CardService crs;
    private final CreditService cds;
    private final DebitService ds;

    @GetMapping
    public String findAll(Model model, @PageableDefault(page = 1)Pageable pageable, HttpSession session) {
        System.out.println("CardController.findAll");
        Long memberId = (Long)session.getAttribute(LOGIN_ID);

        List<CardDetailDTO> cardDetailDTOList = crs.findAll(memberId);
        model.addAttribute("cardList", cardDetailDTOList);

        Page<CardPagingDTO> cardPage = crs.paging(pageable);
        model.addAttribute("cardPage", cardPage);

        int startPage = (((int) (Math.ceil((double)pageable.getPageNumber() / PagingConst.CR_BLOCK_LIMIT))) - 1) * PagingConst.CR_BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.CR_BLOCK_LIMIT - 1) < cardPage.getTotalPages()) ? startPage + PagingConst.CR_BLOCK_LIMIT - 1 : cardPage.getTotalPages();
        model.addAttribute("cardStartPage", startPage);
        model.addAttribute("cardEndPage", endPage);

        return "card/findAll";
    }

    @GetMapping("save")
    public String saveForm(Model model, HttpSession session) {
        System.out.println("CardController.saveForm");
        Long memberId = (Long)session.getAttribute(LOGIN_ID);
        model.addAttribute("member", memberId);

        model.addAttribute("crsave", new CardSaveDTO());

        return "card/save";
    }

    @PostMapping("save")
    public String save(@ModelAttribute CardSaveDTO cardSaveDTO) {
        System.out.println("CardController.save");

        Long cardId = crs.save(cardSaveDTO);

        return "card/findAll";
    }

    @GetMapping("{cardId}")
    public String findById(@PathVariable("cardId") Long cardId, Model model, @PageableDefault(page = 1) Pageable pageable) {
        CardDetailDTO cardDetailDTO = crs.findById(cardId);
        model.addAttribute("cardDTO", cardDetailDTO);

        // 해당 내역을 페이징 화
        int level = cardDetailDTO.getLevel();
        if (level == 0) {
            List<DebitDetailDTO> debitDetailDTOList = ds.findAll(cardId);
            model.addAttribute("dlist", debitDetailDTOList);

            Page<DebitPagingDTO> debitPage = ds.paging(pageable);
            model.addAttribute("cPage", debitPage);

            int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.DR_BLOCK_LIMIT))) - 1) * PagingConst.DR_BLOCK_LIMIT + 1;
            int endPage = ((startPage + PagingConst.DR_BLOCK_LIMIT - 1) < debitPage.getTotalPages()) ? startPage + PagingConst.DR_BLOCK_LIMIT - 1 : debitPage.getTotalPages();
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

        } else {
            List<CreditDetailDTO> creditDetailDTOList = cds.findAll(cardId);
            model.addAttribute("clist", creditDetailDTOList);

            Page<CreditPagingDTO> creditPage = cds.paging(pageable);
            model.addAttribute("cPage", creditPage);

            int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.CD_BLOCK_LIMIT))) - 1) * PagingConst.CD_BLOCK_LIMIT + 1;
            int endPage = ((startPage + PagingConst.CD_BLOCK_LIMIT - 1) < creditPage.getTotalPages()) ? startPage + PagingConst.CR_BLOCK_LIMIT - 1 : creditPage.getTotalPages();
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
        }

        return "card/findById";
    }

    @GetMapping("card/{cardId}")
    public String updateForm(@PathVariable("cardId") Long cardId, Model model) {
        System.out.println("CardController.updateForm");
        CardDetailDTO cardDetailDTO = crs.findById(cardId);

        model.addAttribute("cupdate", cardDetailDTO);

        return "card/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute CardUpdateDTO cardUpdateDTO) {
        Long cardId = crs.update(cardUpdateDTO);

        //페이징?

        return "redirect:/card/findAll";
    }

    @GetMapping("delete/cardId")
    public String delete(@PathVariable("cardId") Long cardId) {
        System.out.println("CardController.delete");
        crs.delete(cardId);

        return "redirect:/card/findAll"; // 이렇게 해야하는가 redirect:/card/라고 해야하는가.
    }
}
