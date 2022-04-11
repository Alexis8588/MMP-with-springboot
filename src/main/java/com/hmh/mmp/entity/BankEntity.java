package com.hmh.mmp.entity;

import com.hmh.mmp.dto.bank.BankDetailDTO;
import com.hmh.mmp.dto.bank.BankSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="bank_table")
public class BankEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long id;

    @Column(length = 30, unique = true)
    private String bankName;

    @Column(length = 1000)
    private String bankMemo;

    private Double totalAsset;
    private Double bankRate;
    private String bankPhotoName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "bankEntity", fetch = FetchType.LAZY)
    private List<AccountEntity> accountEntityList= new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    private CreditEntity creditEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debit_id")
    private DebitEntity debitEntity;

    // BankEntity에다가 Save와 Update등의 내용을 담아야함....
    public static BankEntity saveBank(BankSaveDTO bankSaveDTO, MemberEntity memberEntity) {
        BankEntity bankEntity = new BankEntity();
//        bankEntity.setId(session.getId()); // 어떻게 가지고 와야할까...
//        bankEntity.setMemberId(bankSaveDTO.getMemberId());
//        bankEntity.getMemberId().getId(bankSaveDTO.getMemberId()); // 확인 필요
        // 해당 값을 넣어주지 않아도 알아서 들어가는 것인지에 대해서 확인할 필요가 있음.
        bankEntity.setMemberEntity(memberEntity);
        bankEntity.setBankName(bankSaveDTO.getBankName());
        bankEntity.setBankMemo(bankSaveDTO.getBankMemo());
        bankEntity.setTotalAsset(0.0);
        bankEntity.setBankPhotoName(bankSaveDTO.getBankPhotoName());
        bankEntity.setBankRate(bankSaveDTO.getBankRate());

        return bankEntity;
    }

    public static BankEntity updateBank(BankDetailDTO bankDetailDTO, MemberEntity memberEntity) {
        BankEntity bankEntity = new BankEntity();

        bankEntity.setId(bankDetailDTO.getBankId());
        bankEntity.setMemberEntity(memberEntity);
//        bankEntity.setMemberId(bankDetailDTO.getMemberId()); // 동일한 문제.
        bankEntity.setBankName(bankDetailDTO.getBankName());
        bankEntity.setBankMemo(bankDetailDTO.getBankMemo());
        bankEntity.setTotalAsset(bankDetailDTO.getTotalAsset()); // 업데이트 관련 항목을 찾아봐야함.
        bankEntity.setBankPhotoName(bankDetailDTO.getBankPhotoName());
        bankEntity.setBankRate(bankDetailDTO.getBankRate());

        return bankEntity;
    }
}
