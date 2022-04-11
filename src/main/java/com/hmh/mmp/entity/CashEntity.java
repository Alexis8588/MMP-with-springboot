package com.hmh.mmp.entity;

import com.hmh.mmp.dto.cash.CashSaveDTO;
import com.hmh.mmp.dto.cash.CashUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "cash_table")
public class CashEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cash_id")
    private Long id;

    private String cashName;
    private String cashMemo;

    private String cashPhotoName;
    private Double totalAsset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cashEntity")
    private List<BalanceEntity> balanceEntityList = new ArrayList<>();

    public static CashEntity toSaveCash(CashSaveDTO cashSaveDTO, MemberEntity memberEntity) {
        CashEntity cashEntity = new CashEntity();

        cashEntity.setCashMemo(cashSaveDTO.getCashMemo());
        cashEntity.setCashName(cashSaveDTO.getCashName());
        cashEntity.setMemberEntity(memberEntity);
        cashEntity.setCashPhotoName(cashSaveDTO.getCashPhotoName());
        cashEntity.setTotalAsset(cashSaveDTO.getTotalAsset());

        return cashEntity;
    }

    public static CashEntity toUpdateData(CashUpdateDTO cashUpdateDTO) {
        CashEntity cashEntity = new CashEntity();
        cashEntity.setCashMemo(cashUpdateDTO.getCashMemo());
        cashEntity.setCashName(cashUpdateDTO.getCashName());
        cashEntity.setCashPhotoName(cashUpdateDTO.getCashPhotoName());
        cashEntity.setTotalAsset(cashUpdateDTO.getTotalAsset());

        return cashEntity;
    }
}
