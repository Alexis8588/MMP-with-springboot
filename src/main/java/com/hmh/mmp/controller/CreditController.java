package com.hmh.mmp.controller;

import com.hmh.mmp.dto.bank.BankDetailDTO;
import com.hmh.mmp.dto.credit.CreditDetailDTO;
import com.hmh.mmp.dto.credit.CreditSaveDTO;
import com.hmh.mmp.dto.credit.CreditUpdateDTO;
import com.hmh.mmp.service.BankService;
import com.hmh.mmp.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credit/*")
public class CreditController {
    private final CreditService cds;
    private final BankService bs;

    @GetMapping("save/{cardId}")
    public String saveForm(@PathVariable("cardId") Long cardId, Model model) {
        System.out.println("CreditController.saveForm");
        CreditSaveDTO creditSaveDTO = new CreditSaveDTO();
        creditSaveDTO.setCardId(cardId);
        model.addAttribute("crsave", creditSaveDTO);

        return "credit/save"; // save 개선 필요함
    }

    @PostMapping("save")
    public String save(@ModelAttribute CreditSaveDTO creditSaveDTO) {
        System.out.println("CreditController.save");
        Long creditId = null;
        // 할부 이자 계산 필요
        /*
            날짜는 처음 생성할 때 자동 반영이 됨.
            그러면 달력에 따른 날짜 계산 방법을 추가 해야하는데.?

         */
        // 할부 분활하여 적용하는 방법 필요.
        /*
            할부로 한다고 하면 해당 금액을 정해서 그부분을 일일히 반영할 수 있게 테이블을 적용 해야함.
         */
        if (creditSaveDTO.getMonth() == 0) {
            creditId = cds.save(creditSaveDTO);
        } else {
            // 돈 계산
            Long oneMonthPrice = creditSaveDTO.getMinusAsset() / creditSaveDTO.getMonth();

            // 할부로 할 경우에 1달이 지나면 결제 되야 하는 금액 (할부 금액만을 계산)
//            Long removeAsset = cds.bills(oneMonthPrice);
            // 할부가 아닐때 결제 되어야 하는 금액 (미할부 금액만을 계산)
//            Long restAsset = cds.resolve(creditSaveDTO.getCardId());

            // 매달 결제 되어야 하는 금액은 할부 금액 + 미 할부 금액

            creditId = cds.save(creditSaveDTO);
        }

        return "redirect:/card/" + creditSaveDTO.getCardId();
    }

    @GetMapping("{creditId}")
    public String findById(@PathVariable("creditId") Long creditId, Model model) {
        System.out.println("CreditController.findById");

        CreditDetailDTO creditDetailDTO = cds.findById(creditId);
        model.addAttribute("crDTO", creditDetailDTO);

        return "credit/findById";
    }

    @GetMapping("update/{creditId}")
    public String updateForm(@PathVariable("creditId") Long creditId, Model model) {
        System.out.println("CreditController.updateForm");
        CreditDetailDTO creditDetailDTO = cds.findById(creditId);
        model.addAttribute("crDTO", creditDetailDTO);

        // 계정 정보 가지고 오기.
        List<BankDetailDTO> bankDetailDTOList = bs.findAll(creditDetailDTO.getMemberId());
        model.addAttribute("bList", bankDetailDTOList);

        return "credit/update";
    }

    @GetMapping("delete/{creditId}")
    public String delete(@PathVariable("creditId") Long creditId) {
        System.out.println("CreditController.delete");
        cds.delete(creditId);

        return "card/findAll";
    }

    @PostMapping("update")
    public String update(@ModelAttribute CreditUpdateDTO creditUpdateDTO) {
        System.out.println("CreditController.update");
        Long creditId = cds.update(creditUpdateDTO);

        return "redirect:/credit/findById"; // credit/ + creditId?
    }

    // 할부 이자 계산 필요

    // 할부 분활하여 적용하는 방법 필요.
}
