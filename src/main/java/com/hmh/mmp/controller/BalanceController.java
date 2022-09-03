package com.hmh.mmp.controller;

import com.hmh.mmp.dto.balance.BalanceDetailDTO;
import com.hmh.mmp.dto.balance.BalanceSaveDTO;
import com.hmh.mmp.dto.balance.BalanceUpdateDTO;
import com.hmh.mmp.service.BalanceService;
import com.hmh.mmp.service.BankService;
import com.hmh.mmp.service.CashService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/balance/*")
public class BalanceController {
    private final CashService cs;
    private final BalanceService bas;
    private final BankService bs;

    @GetMapping("{balanceId}")
    public String findById(@PathVariable("balanceId") Long balanceId, Model model) {
        System.out.println("BalanceController.findById");
        BalanceDetailDTO balanceDetailDTO = bas.findById(balanceId);
        model.addAttribute("baDTO", balanceDetailDTO);

        return "balance/findById";
    }

    @GetMapping("delete/{balanceId}")
    public String delete(@PathVariable("balanceId") Long balanceId) {
        System.out.println("BalanceController.delete");
        bas.delete(balanceId);

        return "redirect:/balance/findAll"; // 이거 주소 잘못잡혀있음.
    }

    @GetMapping("update/{balanceId}")
    public String updateForm(@PathVariable("balanceId") Long balanceId, Model model) {
        System.out.println("BalanceController.updateForm");

        BalanceDetailDTO balanceDetailDTO = bas.findById(balanceId);
        model.addAttribute("baDTO", balanceDetailDTO);

        return "balance/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute BalanceUpdateDTO balanceUpdateDTO) throws IOException {
        System.out.println("BalanceController.update");

        Long balanceId = bas.update(balanceUpdateDTO);

        return "redirect:/balance/findById";
    }

    @GetMapping("save/{cashId}")
    public String saveForm(@PathVariable("cashId") Long cashId, Model model) {
        System.out.println("BalanceController.saveForm");
        BalanceSaveDTO balanceSaveDTO = new BalanceSaveDTO();
        balanceSaveDTO.setCashId(cashId);

        model.addAttribute("bsave", balanceSaveDTO);

        return "balance/save"; // save.html을 다시 확인 필요함
    }

    @PostMapping("save")
    public String save(@ModelAttribute BalanceSaveDTO balanceSaveDTO) throws IOException {
        System.out.println("BalanceController.save");
        Long balanceId = bas.save(balanceSaveDTO);

        return "redirect:/cash/findById";
    }
}
