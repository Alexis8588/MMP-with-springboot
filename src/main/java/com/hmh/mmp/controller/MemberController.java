package com.hmh.mmp.controller;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.account.AccountDetailDTO;
import com.hmh.mmp.dto.account.AccountPagingDTO;
import com.hmh.mmp.dto.balance.BalanceDetailDTO;
import com.hmh.mmp.dto.balance.BalancePagingDTO;
import com.hmh.mmp.dto.bank.BankDetailDTO;
import com.hmh.mmp.dto.bank.BankPagingDTO;
import com.hmh.mmp.dto.board.BoardDetailDTO;
import com.hmh.mmp.dto.board.BoardPagingDTO;
import com.hmh.mmp.dto.card.CardDetailDTO;
import com.hmh.mmp.dto.card.CardPagingDTO;
import com.hmh.mmp.dto.cash.CashDetailDTO;
import com.hmh.mmp.dto.cash.CashPagingDTO;
import com.hmh.mmp.dto.credit.CreditDetailDTO;
import com.hmh.mmp.dto.credit.CreditPagingDTO;
import com.hmh.mmp.dto.debit.DebitDetailDTO;
import com.hmh.mmp.dto.debit.DebitPagingDTO;
import com.hmh.mmp.dto.member.MemberDetailDTO;
import com.hmh.mmp.dto.member.MemberLoginDTO;
import com.hmh.mmp.dto.member.MemberPagingDTO;
import com.hmh.mmp.dto.member.MemberSaveDTO;
import com.hmh.mmp.dto.notice.NoticeDetailDTO;
import com.hmh.mmp.dto.notice.NoticePagingDTO;
import com.hmh.mmp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.hmh.mmp.common.SessionConst.*;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor // final이 붙어있는ㄱ ㅓㅅ에 대한 생성자를 만들어줌?
public class MemberController {
    private final MemberService ms;
    private final BankService bs;
    private final BoardService bos;
    private final CardService crs;
    private final CashService css;
    private final AccountService as;
    private final BalanceService bas;
    private final DebitService dbs;
    private final CreditService cds;
    private final NoticeService ns;

    @GetMapping("save")
    public String saveForm(Model model) {
        model.addAttribute("msave", new MemberSaveDTO()); // new MemberSaveDTO() 는 생성자임.

        return "member/save";
    }

    @PostMapping("save")
    public String save(@PathVariable @ModelAttribute("msave") MemberSaveDTO memberSaveDTO, BindingResult br) throws IOException {
        // @ModelAttribute의 member는 대체 어디서 넘어온 것인가.
        if (br.hasErrors()) {
            return "member/save";
        }

        // 이메일 중복 처리
        try {
            ms.save(memberSaveDTO);
        } catch (IllegalStateException e) {
            br.reject("eCheck", e.getMessage());
        }

        return "index";
    }

    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("mlogin", new MemberSaveDTO());

        return "member/login";
    }

    @PostMapping("login")
    public String login(@PathVariable @ModelAttribute("mlogin") MemberLoginDTO memberLoginDTO, BindingResult br, HttpSession session, Model model, Pageable pageable) {
        boolean checkResult = ms.login(memberLoginDTO); // MemberLoginDTO에 다가 Entity데이터를 담아서 비교?

        if (checkResult) {
            session.setAttribute("loginEmail", memberLoginDTO.getMemberEmail());
            // 해당 loginEmail의 값을 SessionConst라는 폴더를 만들어서 적용도 가능함.
            session.setAttribute(LOGIN_EMAIL, memberLoginDTO.getMemberEmail());
            session.setAttribute("loginId", memberLoginDTO.getId());
            session.setAttribute(LOGIN_ID, memberLoginDTO.getId());
            session.setAttribute("loginNickName", memberLoginDTO.getMemberNickName());
            session.setAttribute(NICK_NAME, memberLoginDTO.getMemberNickName());

            // 여기서 페이징 부터 해서 모든 데이터를 메인에 뿌려 줘야함.
            // 은행 관련
            List<BankDetailDTO> bankList = bs.findAll(memberLoginDTO.getId());
            model.addAttribute("bList", bankList);

            Page<BankPagingDTO> bPageList = bs.paging(pageable);
            model.addAttribute("bPage", bPageList);
                // 해당 내용 메서드로 만들지 고민....
            int bank_startPage = (((int) (Math.ceil((double)pageable.getPageNumber() / PagingConst.B_BLOCK_LIMIT))) - 1) * PagingConst.B_BLOCK_LIMIT + 1;
            int bank_endPage = ((bank_startPage + PagingConst.B_BLOCK_LIMIT - 1) < bPageList.getTotalPages()) ? bank_startPage + PagingConst.B_BLOCK_LIMIT - 1 : bPageList.getTotalPages();
            model.addAttribute("bank_startPage", bank_startPage);
            model.addAttribute("bank_endPage", bank_endPage);

            // 카드 관련
            List<CardDetailDTO> cardList = crs.findAll(memberLoginDTO.getId());
            model.addAttribute("crList", cardList);

            Page<CardPagingDTO> crPageList = crs.paging(pageable);
            model.addAttribute("crPage", crPageList);
            int card_startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.CR_BLOCK_LIMIT))) - 1) * PagingConst.CR_BLOCK_LIMIT + 1;
            int card_endPage = ((card_startPage + PagingConst.CR_BLOCK_LIMIT - 1) < crPageList.getTotalPages()) ? card_startPage + PagingConst.CR_BLOCK_LIMIT - 1 : crPageList.getTotalPages();
            model.addAttribute("card_startPage", card_startPage);
            model.addAttribute("card_endPage", card_endPage);

            // 현금 관련
            List<CashDetailDTO> cashList = css.findAll(memberLoginDTO.getId());
            model.addAttribute("csList", cashList);

            Page<CashPagingDTO> caPagingList = css.paging(pageable);
            model.addAttribute("caPage", caPagingList);
            int cash_startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.CS_BLOCK_LIMIT))) - 1) * PagingConst.CS_BLOCK_LIMIT + 1;
            int cash_endPage = ((cash_startPage + PagingConst.CS_BLOCK_LIMIT - 1) < caPagingList.getTotalPages()) ? cash_startPage + PagingConst.CS_BLOCK_LIMIT - 1 : caPagingList.getTotalPages();
            model.addAttribute("cash_startPage", cash_startPage);
            model.addAttribute("cash_endPage", cash_endPage);

            // 공지사항 관련
            // Notice 관련 정보 불러오기
            List<NoticeDetailDTO> noticeDetailDTOList = ns.findAll();
            model.addAttribute("noList", noticeDetailDTOList);
            Page<NoticePagingDTO> noPagingList = ns.paging(pageable);
            model.addAttribute("noPage", noPagingList);
            int notice_startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.N_BLOCK_LIMIT))) - 1) * PagingConst.N_BLOCK_LIMIT + 1;
            int notice_endPage = ((notice_startPage + PagingConst.N_BLOCK_LIMIT - 1) < noPagingList.getTotalPages()) ? notice_startPage + PagingConst.N_BLOCK_LIMIT - 1 : noPagingList.getTotalPages();
            model.addAttribute("notice_startPage", notice_startPage);
            model.addAttribute("notice_endPage", notice_endPage);

            // 게시판 관련
            // board 관련 정보 불러오기
            List<BoardDetailDTO> boardDetailDTOList = bos.findAll();
            model.addAttribute("boList", boardDetailDTOList);
            Page<BoardPagingDTO> boPagingList = bos.paging(pageable);
            model.addAttribute("boPage", boPagingList);
            int board_startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.BO_BLOCK_LIMIT))) - 1) * PagingConst.BO_BLOCK_LIMIT + 1;
            int board_endPage = ((board_startPage + PagingConst.BO_BLOCK_LIMIT - 1) < boPagingList.getTotalPages()) ? board_startPage + PagingConst.BO_BLOCK_LIMIT - 1 : boPagingList.getTotalPages();
            model.addAttribute("board_startPage", board_startPage);
            model.addAttribute("board_endPage", board_endPage);

            // account 관련 정보 불러오기
            // bankId로 하면 한개의 bankId만을 불러올 것이고 accountId는 해당 되지 않음. 때문에 memberId를 통해서 불러와야하는데. memberId의 경우 다양한 배열이 존재할 것이기 때문에 해당 데이터를 어떻게 불러와야 할지 고안이 필요함.
            List<AccountDetailDTO> accountDetailDTOList = as.findData(memberLoginDTO.getId());
            model.addAttribute("acList", accountDetailDTOList);
            Page<AccountPagingDTO> acPagingList = as.paging(pageable);
            model.addAttribute("acPage", acPagingList);
            int account_startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.A_BLOCK_LIMIT))) - 1) * PagingConst.A_BLOCK_LIMIT + 1;
            int account_endPage = ((account_startPage + PagingConst.A_BLOCK_LIMIT - 1) < acPagingList.getTotalPages()) ? account_startPage + PagingConst.A_BLOCK_LIMIT - 1 : acPagingList.getTotalPages();
            model.addAttribute("account_startPage", account_startPage);
            model.addAttribute("account_endPage", account_endPage);

            // credit 관련 정보 불러오기
            List<CreditDetailDTO> creditDetailDTOList = cds.findData(memberLoginDTO.getId());
            model.addAttribute("cdList", creditDetailDTOList);
            Page<CreditPagingDTO> cdPagingList = cds.paging(pageable);
            model.addAttribute("cdPage", cdPagingList);
            int credit_startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.CD_BLOCK_LIMIT))) - 1) * PagingConst.CD_BLOCK_LIMIT + 1;
            int credit_endPage = ((credit_startPage + PagingConst.CD_BLOCK_LIMIT - 1) < cdPagingList.getTotalPages()) ? credit_startPage + PagingConst.CD_BLOCK_LIMIT - 1 : cdPagingList.getTotalPages();
            model.addAttribute("credit_startPage", credit_startPage);
            model.addAttribute("credit_endPage", credit_endPage);

            // debit 관련 정보 불러오기
            List<DebitDetailDTO> debitDetailDTOList = dbs.findData(memberLoginDTO.getId());
            model.addAttribute("dbList", debitDetailDTOList);
            Page<DebitPagingDTO> dbPagingList = dbs.paging(pageable);
            model.addAttribute("dbPage", dbPagingList);
            int debit_startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.DR_BLOCK_LIMIT))) - 1) * PagingConst.DR_BLOCK_LIMIT + 1;
            int debit_endPage = ((debit_startPage + PagingConst.DR_BLOCK_LIMIT - 1) < dbPagingList.getTotalPages()) ? debit_startPage + PagingConst.DR_BLOCK_LIMIT - 1 : dbPagingList.getTotalPages();
            model.addAttribute("debit_startPage", debit_startPage);
            model.addAttribute("debit_endPage", debit_endPage);

            // balance 관련 정보 불러오기
            List<BalanceDetailDTO> balanceDetailDTOList = bas.findData(memberLoginDTO.getId());
            model.addAttribute("baList", balanceDetailDTOList);
            Page<BalancePagingDTO> baPagingList = bas.paging(pageable);
            model.addAttribute("baPage", baPagingList);
            int balance_startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.BA_BLOCK_LIMIT))) - 1) * PagingConst.BA_BLOCK_LIMIT + 1;
            int balance_endPage = ((balance_startPage + PagingConst.BA_BLOCK_LIMIT - 1) < baPagingList.getTotalPages()) ? balance_startPage + PagingConst.BA_BLOCK_LIMIT - 1 : baPagingList.getTotalPages();
            model.addAttribute("balance_startPage", balance_startPage);
            model.addAttribute("balance_endPage", balance_endPage);

            // member 관련 정보 불러오기
            List<MemberDetailDTO> memberDetailDTOList = ms.findAll();
            model.addAttribute("memberList", memberDetailDTOList);
            Page<MemberPagingDTO> mPagingList = ms.paging(pageable);
            model.addAttribute("mPage", mPagingList);
            int member_startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
            int member_endPage = ((member_startPage + PagingConst.BLOCK_LIMIT - 1) < mPagingList.getTotalPages()) ? member_startPage + PagingConst.BLOCK_LIMIT - 1 : mPagingList.getTotalPages();
            model.addAttribute("member_startPage", member_startPage);
            model.addAttribute("member_endPage", member_endPage);

            return "redirect:/main";
        } else {
            return "member/login";
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "index";
    }

    @GetMapping
    public String findAll(Model model, @PageableDefault(page = 1) Pageable pageable) {
        List<MemberDetailDTO> memberDetailDTO = ms.findAll();
        model.addAttribute("mList", memberDetailDTO);

        // 페이지 작업 하기.
        Page<MemberPagingDTO> mPageList = ms.paging(pageable);
        model.addAttribute("pList", mPageList);

        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < mPageList.getTotalPages()) ? startPage * PagingConst.BLOCK_LIMIT - 1 : mPageList.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "member/findAll";
    }

    @PostMapping("{memberId}") // ajax로 받음.
    public @ResponseBody MemberDetailDTO findById(@PathVariable("memberId") Long memberId) {
        MemberDetailDTO memberDetailDTO = ms.findById(memberId);

        return memberDetailDTO;
    }

    // 로그인 한 사람이 자기 정보 조회
    @GetMapping("detail")
    public String detail(Model model, HttpSession session) {
        String memberEmail = (String) session.getAttribute("loginEmail");

        MemberDetailDTO memberDetailDTO = ms.findByEmail(memberEmail);
        model.addAttribute("mdDTO", memberDetailDTO);

        return "member/detail";
    }

    @DeleteMapping("{memberId}")
    public ResponseEntity delete(@PathVariable("memberId") Long memberId) {
        ms.delete(memberId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("update")
    public String updatePost(@ModelAttribute MemberDetailDTO memberDetailDTO) {
        Long memberId = ms.update(memberDetailDTO);

        return "redirect:/member/" + memberDetailDTO.getMemberId();
    }

    @GetMapping("select")
    public String select(Model model, HttpSession session, MemberLoginDTO memberLoginDTO) {
        Long memberId = memberLoginDTO.getId();
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("mList", memberList);

        List<BankDetailDTO> bankList = bs.findAll(memberLoginDTO.getId());// 해당 관련 memberId 관련 넣어줘야함.
        model.addAttribute("bList", bankList);



//        bs.findAll(memberId); // 일시 정지

        return "/select";
    }
}
