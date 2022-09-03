package com.hmh.mmp.dto.account;

import com.hmh.mmp.entity.BankEntity;
import com.hmh.mmp.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSaveDTO{
    private Long bankId;
    private Long memberId;

    private LocalDate date;
    private double plusAsset;
    private double minusAsset;
    private String accountName;
    private MultipartFile accountPhoto;
    private String accountPhotoName;
    private String accountMemo;

    private List<String> firstList;
//    private String firstList; // 대분류
    private String secondList; // 중뷴류
    private String thirdList; // 소분류

}
