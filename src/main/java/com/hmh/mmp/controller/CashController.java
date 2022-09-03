package com.hmh.mmp.controller;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.balance.BalanceDetailDTO;
import com.hmh.mmp.dto.balance.BalancePagingDTO;
import com.hmh.mmp.dto.cash.CashDetailDTO;
import com.hmh.mmp.dto.cash.CashPagingDTO;
import com.hmh.mmp.dto.cash.CashSaveDTO;
import com.hmh.mmp.dto.cash.CashUpdateDTO;
import com.hmh.mmp.dto.member.MemberDetailDTO;
import com.hmh.mmp.service.BalanceService;
import com.hmh.mmp.service.CashService;
import com.hmh.mmp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static com.hmh.mmp.common.SessionConst.LOGIN_EMAIL;
import static com.hmh.mmp.common.SessionConst.LOGIN_ID;

@Controller
@RequestMapping("/cash/*")
@RequiredArgsConstructor
public class CashController {
    private final CashService css;
    private final BalanceService bas;
    private final MemberService ms;

    @GetMapping("save")
    public String saveForm(Model model) {
        model.addAttribute("csave", new CashSaveDTO());

        return "cash/save";
    }

    @PostMapping("save")
    public String save(@PathVariable @ModelAttribute("csave") CashSaveDTO cashSaveDTO, HttpSession session, BindingResult br) throws IOException {
        String memberEmail = (String)session.getAttribute(LOGIN_EMAIL);
        MemberDetailDTO memberDetailDTO = ms.findByEmail(memberEmail);
        Long memberId = memberDetailDTO.getMemberId();

        cashSaveDTO.setMemberId(memberId);

        Long cashId = css.save(cashSaveDTO);

        return "cash/findAll";
    }

    @GetMapping
    public String findAll(Model model, @PageableDefault(page = 1)Pageable pageable, HttpSession session) {
        Long memberId = (Long)session.getAttribute(LOGIN_ID);
        MemberDetailDTO memberDetailDTO = ms.findById(memberId);

        List<CashDetailDTO> cashDetailDTOList = css.findAll(memberId);
        model.addAttribute("cashList", cashDetailDTOList);

        Page<CashPagingDTO> cashPagingDTOList = css.paging(pageable);
        model.addAttribute("cashPage", cashPagingDTOList);

        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.CS_BLOCK_LIMIT))) - 1) * PagingConst.CS_BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.CS_BLOCK_LIMIT - 1) < cashPagingDTOList.getTotalPages()) ? startPage + PagingConst.CS_BLOCK_LIMIT - 1 : cashPagingDTOList.getTotalPages();

        model.addAttribute("cashStartPage", startPage);
        model.addAttribute("cashEndPage", endPage);

        return "cash/findAll";
    }

    @GetMapping("(cashId}")
    public String findById(Model model, @PageableDefault(page = 1) Pageable pageable, @PathVariable("cashId") Long cashId) {
        System.out.println("CashController.findById");

        CashDetailDTO cashDetailDTO = css.findById(cashId);
        model.addAttribute("cashDTO", cashDetailDTO);

        // balance에 대한 내역 불러와서 페이징 처리 필요
        List<BalanceDetailDTO> balanceDetailDTOList = bas.findAll(cashId);
        model.addAttribute("baList", balanceDetailDTOList);

        Page<BalancePagingDTO> balancePaging = bas.paging(pageable);
        model.addAttribute("balancePage", balancePaging);

        int startPage = (((int) (Math.ceil((double)pageable.getPageNumber() / PagingConst.BA_BLOCK_LIMIT))) - 1) * PagingConst.BA_BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BA_BLOCK_LIMIT - 1) < balancePaging.getTotalPages()) ? startPage + PagingConst.BA_BLOCK_LIMIT - 1 : balancePaging.getTotalPages();
        model.addAttribute("balanceStartPage", startPage);
        model.addAttribute("balanceEndPage", endPage);

        return "cash/findById";
    }

    @GetMapping("update/{cashId}")
    public String updateForm(@PathVariable("cashId") Long cashId, Model model) {
        System.out.println("CashController.updateForm");

        CashDetailDTO cashDetailDTO = css.findById(cashId);
        model.addAttribute("cashDTO", cashDetailDTO);

        return "cash/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute CashUpdateDTO cashUpdateDTO) {
        System.out.println("CashController.update");
        Long cashId = css.update(cashUpdateDTO);

        return "cash/findAll";
    }

    @GetMapping("delete/{cashId}")
    public String delete(@PathVariable("cashId") Long cashId) {
        System.out.println("CashController.delete");
        css.delete(cashId);

        return "redirect:/cash/findAll";
    }

}
