package com.hmh.mmp.controller;

import com.hmh.mmp.dto.bank.BankDetailDTO;
import com.hmh.mmp.dto.card.CardDetailDTO;
import com.hmh.mmp.dto.debit.DebitDetailDTO;
import com.hmh.mmp.dto.debit.DebitSaveDTO;
import com.hmh.mmp.dto.debit.DebitUpdateDTO;
import com.hmh.mmp.dto.member.MemberDetailDTO;
import com.hmh.mmp.service.BankService;
import com.hmh.mmp.service.CardService;
import com.hmh.mmp.service.DebitService;
import com.hmh.mmp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/debit/*")
public class DebitController {
    private final DebitService ds;
    private final CardService cs;
    private final BankService bs;

    @GetMapping("save/{cardId}")
    public String saveForm(@PathVariable("cardId") Long cardId, Model model) {
        System.out.println("DebitController.saveForm");
        DebitSaveDTO debitSaveDTO = new DebitSaveDTO();
        debitSaveDTO.setCardId(cardId);

        model.addAttribute("dsave", debitSaveDTO);

        CardDetailDTO cardDetailDTO = cs.findById(cardId);
        List<BankDetailDTO> bankDetailDTOList = bs.findAll(cardDetailDTO.getMemberId());
        model.addAttribute("bList", bankDetailDTOList);

        return "debit/save";
    }

    @PostMapping("save")
    public String save(@ModelAttribute DebitSaveDTO debitSaveDTO) throws IOException {
        System.out.println("DebitController.save");
        Long debitId = ds.save(debitSaveDTO);

        return "redirect:/card/findAll";
    }

    @GetMapping("{debitId}")
    public String findById(@PathVariable("debitId") Long debitId, Model model) {
        System.out.println("DebitController.findById");

        DebitDetailDTO debitDetailDTO = ds.findById(debitId);
        model.addAttribute("deDTO", debitDetailDTO);

        return "debit/findById";
    }

    @GetMapping("delete/{debitId}")
    public String delete(@PathVariable("debitId") Long debitId) {
        System.out.println("DebitController.delete");
        DebitDetailDTO debitDetailDTO = ds.findById(debitId);
        Long cardId = debitDetailDTO.getCardId();

        ds.delete(debitId);

        return "redirect:/card/" + cardId;
    }

    @GetMapping("update/{debitId}")
    public String updateForm(@PathVariable("debitId") Long debitId, Model model) {
        System.out.println("DebitController.updateForm");
        DebitDetailDTO debitDetailDTO = ds.findById(debitId);
        model.addAttribute("deDTO", debitDetailDTO);

        List<BankDetailDTO> bankDetailDTOList = bs.findAll(debitDetailDTO.getMemberId());
        model.addAttribute("bList", bankDetailDTOList);

        return "debit/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute DebitUpdateDTO debitUpdateDTO) {
        System.out.println("DebitController.update");
        Long debitId = ds.update(debitUpdateDTO);

        return "debit/findById";
    }

}
