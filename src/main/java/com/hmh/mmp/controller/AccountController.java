package com.hmh.mmp.controller;

import com.hmh.mmp.dto.account.AccountDetailDTO;
import com.hmh.mmp.dto.account.AccountSaveDTO;
import com.hmh.mmp.dto.account.AccountUpdateDTO;
import com.hmh.mmp.dto.bank.BankDetailDTO;
import com.hmh.mmp.service.AccountService;
import com.hmh.mmp.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/account/*")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService as;
    private final BankService bs;

    @GetMapping("save/{bankId}")
    public String saveForm(@PathVariable("bankId") Long bankId, Model model) {
        System.out.println("AccountController.saveForm");
        BankDetailDTO bankDetailDTO = bs.findById(bankId);

        model.addAttribute("bankId", bankId);
        model.addAttribute("memberId", bankDetailDTO.getMemberId());
        model.addAttribute("bankName", bankDetailDTO.getBankName());

        model.addAttribute("accountsave", new AccountSaveDTO());

        return "account/save";
    }

    @PostMapping("save")
    public String save(@ModelAttribute AccountSaveDTO accountSaveDTO, Model model) throws IOException {
        System.out.println("AccountController.save");
        Long accountId = as.save(accountSaveDTO);

        // Paging 관련 업데이트 필요함
//        List<AccountDetailDTO> accountDetailDTOList = as.findAll(accountSaveDTO.getBankId());
//        model.addAttribute("aList", accountDetailDTOList);

        return "bank/findById";
    }

    @GetMapping("{accountId}")
    public String findById(@PathVariable("accountId") Long accountId, Model model) {
        System.out.println("AccountController.findById");

        AccountDetailDTO accountDetailDTO = as.findById(accountId);
        model.addAttribute("aDTO", accountDetailDTO);

        return "account/findById";
    }

    @GetMapping("update/{accountId}")
    public String updateForm(@PathVariable("accountId") Long accountId, Model model) {
        System.out.println("AccountController.updateForm");

        AccountDetailDTO accountDetailDTO = as.findById(accountId);
        model.addAttribute("aDTO", accountDetailDTO);

        return "account/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute AccountUpdateDTO accountUpdateDTO) throws IOException {
        System.out.println("AccountController.update");
        Long accountId = as.update(accountUpdateDTO);

        return "redirect:/account/findById" + accountId;
    }

    @GetMapping("delete/{accountId}")
    public String delete(@PathVariable("accountId") Long accountId) {
        System.out.println("AccountController.delete");
        as.delete(accountId);

        return "redirect:/account/findAll"; // /account/로 해야하나?
    }
}
