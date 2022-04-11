package com.hmh.mmp.entity;

import com.hmh.mmp.dto.account.AccountSaveDTO;
import com.hmh.mmp.dto.account.AccountUpdateDTO;
import com.hmh.mmp.entity.list.FirstListEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 은행 관련 내역을 보기 위한 것.
@Entity
@Getter
@Setter
@Table(name = "account_table")
public class AccountEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountEntity")
    private List<FirstListEntity> firstListEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private BankEntity bankEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    private LocalDateTime calTime;
    private LocalDate   calDate;
    private Double plusAsset;
    private Double minusAsset;
    private String accountName; // 후에 고민해보기
    private String accountPhotoName;
    private String accountMemo;
    private LocalDate date;

    public static AccountEntity toSaveData(AccountSaveDTO accountSaveDTO, BankEntity bankEntity) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountName(accountSaveDTO.getAccountName());
        accountEntity.setAccountMemo(accountSaveDTO.getAccountMemo());
        accountEntity.setAccountPhotoName(accountSaveDTO.getAccountPhotoName());
        accountEntity.setBankEntity(bankEntity);
        accountEntity.setMinusAsset(accountSaveDTO.getMinusAsset());
        accountEntity.setPlusAsset(accountSaveDTO.getPlusAsset());
        accountEntity.setDate(accountSaveDTO.getDate());

        return accountEntity;

    }

    public static AccountEntity toSaveDataWithMember(AccountEntity accountEntity, BankEntity bankEntity, MemberEntity memberEntity) {
        AccountEntity accountEntityWithMember = new AccountEntity();
        return accountEntity;
    }

    public static AccountEntity toUpdateData(AccountUpdateDTO accountUpdateDTO) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountMemo(accountUpdateDTO.getAccountMemo());
        accountEntity.setAccountName(accountUpdateDTO.getAccountName());
        accountEntity.setAccountPhotoName(accountUpdateDTO.getAccountPhotoName());
        accountEntity.setPlusAsset(accountUpdateDTO.getPlusAsset());
        accountEntity.setMinusAsset(accountUpdateDTO.getMinusAsset());
        accountEntity.setDate(accountUpdateDTO.getDate());

        return accountEntity;
    }
}
