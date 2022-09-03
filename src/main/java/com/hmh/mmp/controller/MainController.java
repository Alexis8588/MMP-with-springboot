package com.hmh.mmp.controller;

import com.hmh.mmp.dto.MainDetailDTO;
import com.hmh.mmp.dto.account.AccountDetailDTO;
import com.hmh.mmp.dto.balance.BalanceDetailDTO;
import com.hmh.mmp.dto.bank.BankDetailDTO;
import com.hmh.mmp.dto.board.BoardDetailDTO;
import com.hmh.mmp.dto.card.CardDetailDTO;
import com.hmh.mmp.dto.cash.CashDetailDTO;
import com.hmh.mmp.dto.credit.CreditDetailDTO;
import com.hmh.mmp.dto.debit.DebitDetailDTO;
import com.hmh.mmp.dto.member.MemberDetailDTO;
import com.hmh.mmp.dto.notice.NoticeDetailDTO;
import com.hmh.mmp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.hmh.mmp.common.SessionConst.LOGIN_ID;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MemberService ms;
    private final BankService bs;
    private final AccountService as;
    private final CashService css;
    private final BalanceService bas;
    private final CardService crs;
    private final DebitService dbs;
    private final CreditService cds;
    private final BoardService bos;
    private final NoticeService ns;

    @GetMapping("/") // 이걸 주소를 이대로 가져가야 하는지 아니면 ()를 지우고 아무것도 없게 해야하는지?
    public String index() {

        return "index";
    }

    @GetMapping("select")
    public String createForm(HttpSession session, Model model) {
//        String memberEmail = (String)session.getAttribute("loginEmail");
//
//        // DetailDTO 만들기 전에 중지. (Save로 해도 되지만 해당 session에서 관련 계정의 데이터를 가져와야지 무분별하게 전체 데이터에서 가지고 오면 안디니까....
        Long memberId = (Long) session.getAttribute(LOGIN_ID);

        return "create";
    }

    @GetMapping("graph")
    public String stickGraph(Model model) {

        return "main";
    }

    @GetMapping("selectform")
    public String selectForm(Model model, @RequestParam("select") Long select, HttpSession session) {
        System.out.println("MainController.select");
        Long memberId = (Long)session.getAttribute("LoginId");
        MemberDetailDTO memberDetailDTO = ms.findById(memberId);
        BankDetailDTO bankDetailDTO = bs.findById(memberId);
        CashDetailDTO cashDetailDTO = css.findById(memberId);
        List<BankDetailDTO> bankDetailDTOList = bs.findAll(memberId);
        List<CashDetailDTO> cashDetailDTOList = css.findAll(memberId);
        MainDetailDTO mainDetailDTO = new MainDetailDTO();
        model.addAttribute("main", mainDetailDTO);

        if (select == 1) { // 현금에서 계좌
            model.addAttribute("select", select);
            model.addAttribute("cDTO", cashDetailDTO);
            model.addAttribute("bList", bankDetailDTOList);

            return "cash/transfer";
        } else if (select == 2) { // 계좌에서 현금
            model.addAttribute("select", select);
            model.addAttribute("bDTO", bankDetailDTO);
            model.addAttribute("bList", bankDetailDTOList);

            return "bank/transfer";
        } else if (select == 3) { // 계좌에서 계좌
            model.addAttribute("select", select);
            model.addAttribute("bDTO2", bankDetailDTO);
            model.addAttribute("bList2", bankDetailDTOList);

            return "cash/transfer";
        } else if (select == 4) { // 현금에서 현금
            model.addAttribute("select", select);
            model.addAttribute("cDTO2", cashDetailDTO);
            model.addAttribute("cList2", cashDetailDTOList);

            return "bank/transfer";
        } else {

            return "main";
        }
    }

    @PostMapping("select")
    public String select(Model model, @PathVariable("select") Long select, @ModelAttribute MainDetailDTO mainDetailDTO, HttpSession session) {
        System.out.println("MainController.select");


        return "redirect:/main";
    }
}
