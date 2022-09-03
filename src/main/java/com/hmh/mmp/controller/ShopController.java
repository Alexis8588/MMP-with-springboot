package com.hmh.mmp.controller;

import com.hmh.mmp.dto.member.MemberDetailDTO;
import com.hmh.mmp.service.MemberService;
import com.hmh.mmp.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.hmh.mmp.common.SessionConst.LOGIN_ID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shop/*")
public class ShopController {
    private final ShopService ss;
    private final MemberService ms;

    @GetMapping
    public String findAll(Model model, HttpSession session, @PageableDefault(page = 1)Pageable pageable) {
        System.out.println("ShopController.findAll");
        Long memberId = (Long)session.getAttribute(LOGIN_ID);
        MemberDetailDTO memberDetailDTO = ms.findById(memberId);

        return "shop/findAll";
    }
}
