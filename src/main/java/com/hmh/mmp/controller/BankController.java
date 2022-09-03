package com.hmh.mmp.controller;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.account.AccountDetailDTO;
import com.hmh.mmp.dto.account.AccountPagingDTO;
import com.hmh.mmp.dto.bank.BankDetailDTO;
import com.hmh.mmp.dto.bank.BankPagingDTO;
import com.hmh.mmp.dto.bank.BankSaveDTO;
import com.hmh.mmp.dto.member.MemberDetailDTO;
import com.hmh.mmp.dto.member.MemberLoginDTO;
import com.hmh.mmp.service.AccountService;
import com.hmh.mmp.service.BankService;
import com.hmh.mmp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/bank/*")
public class BankController {
    private final MemberService ms;
    private final BankService bs;
    private final AccountService as;

    @GetMapping("save")
    public String saveForm(Model model, HttpSession session) { //, MemberLoginDTO memberLoginDTO) {
        // 해당 값을 가지고 와야 하는데 해당 LoginData 에서 가지고 와야하는지?
//        Long memberId = memberLoginDTO.getId();
//        System.out.println("로그인 된 memberId : " + memberId);
//
//        model.addAttribute("memberId", memberId);
        model.addAttribute("msave", session.getAttribute(LOGIN_ID));
        model.addAttribute("bsave", new BankSaveDTO());

        return "bank/save";
    }

    @PostMapping("save")
    public String save(@PathVariable @ModelAttribute("bsave") BankSaveDTO bankSaveDTO, HttpSession session, BindingResult br) throws IOException {
        if (br.hasErrors()) {

            return "bank/save";
        }

//        try {
//            bs.save(bankSaveDTO);
//        } catch (IllegalStateException e) {
//            br.reject("bCheck", e.getMessage());
//        }
        String memberEmail = (String)session.getAttribute(LOGIN_EMAIL);
        MemberDetailDTO memberDetailDTO = ms.findByEmail(memberEmail);

        bankSaveDTO.setMemberId(memberDetailDTO.getMemberId());

        Long bankId = bs.save(bankSaveDTO);

        return "redirect:/main";
    }

    @GetMapping
    public String findAll(Model model, HttpSession session, MemberLoginDTO memberLoginDTO, @PageableDefault(page = 1) Pageable pageable) {
        Long memberId = memberLoginDTO.getId();

        List<BankDetailDTO> bankDetailDTOList = bs.findAll(memberId);
        model.addAttribute("bdList", bankDetailDTOList);

        Page<BankPagingDTO> bPageList = bs.paging(pageable);
        model.addAttribute("bpList", bPageList);

        int startPage = (((int) (Math.ceil((double)pageable.getPageNumber() / PagingConst.B_BLOCK_LIMIT))) - 1) * PagingConst.B_BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.B_BLOCK_LIMIT - 1) < bPageList.getTotalPages()) ? startPage + PagingConst.B_BLOCK_LIMIT - 1 : bPageList.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "bank/findAll";
    }

    @GetMapping("{bankId}")
    public String findById(@PathVariable("bankId") Long bankId, Model model, @PageableDefault(page = 1) Pageable pageable) {
        System.out.println("BankController.findById");
        log.info("글보기 메서드 호출. 요청글 번호 : {} ", bankId);

        BankDetailDTO bankDetailDTO = bs.findById(bankId);
        model.addAttribute("bdDTO", bankDetailDTO);

        // account 내역 부르기는 이거로 대신함... account에서 부르지 않음.
        List<AccountDetailDTO> accountDetailDTOList = as.findAll(bankId);
        model.addAttribute("aList", accountDetailDTOList);

        Page<AccountPagingDTO> accountPagingDTOPage = as.paging(pageable);
        model.addAttribute("accountPage", accountPagingDTOPage);

        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.A_BLOCK_LIMIT))) - 1) * PagingConst.A_BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.A_BLOCK_LIMIT - 1) < accountPagingDTOPage.getTotalPages()) ? startPage + PagingConst.A_BLOCK_LIMIT : accountPagingDTOPage.getTotalPages();
        model.addAttribute("aStartPage", startPage);
        model.addAttribute("aEndPage", endPage);

//        return "bank/findById";
        return "bank/findById" + bankId;
    }

    @GetMapping("update")
    public String updateForm(@PathVariable("bankId") Long bankId, Model model) {
        System.out.println("BankController.updateForm");
        BankDetailDTO bankDetailDTO = bs.findById(bankId);

        model.addAttribute("bankDTO", bankDetailDTO);

        return "bank/update";
    }

    @PostMapping("{bankId}")
    public String update(@ModelAttribute BankDetailDTO bankDetailDTO) throws IOException {
        System.out.println("BankController.update");
        Long bankId = bs.update(bankDetailDTO);

//        return "redirect:/bank/" + bankDetailDTO.getBankId();
        return "bank/findAll";
    }

    @GetMapping("delete/{bankId}")
    public String delete(@PathVariable("bankId") Long bankId) {
        System.out.println("BankController.delete");

        bs.delete(bankId);

        return "redirect:/bank/";
    }

}
