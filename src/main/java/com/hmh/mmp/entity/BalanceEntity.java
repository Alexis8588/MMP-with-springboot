package com.hmh.mmp.entity;

import com.hmh.mmp.dto.balance.BalanceSaveDTO;
import com.hmh.mmp.dto.balance.BalanceUpdateDTO;
import com.hmh.mmp.entity.list.FirstListEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 현금 관련 내역을 보기 위한 것.
@Entity
@Getter
@Setter
@Table(name = "balance_table")
public class BalanceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_id")
    private CashEntity cashEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "balanceEntity")
    private List<FirstListEntity> firstListEntityList = new ArrayList<>();

    private LocalDate calDate;
    private LocalDate date;
    private LocalDateTime calTime;
    private String balanceMemo;
    private String balanceName;
    private Double plusAsset;
    private Double minusAsset;

    private Double balanceGet; // 할인액
    private Double balancePercents; // 할인율

    private Double rate; // 이자?
    private String balancePhotoName;


    public static BalanceEntity toUpdateData(BalanceUpdateDTO balanceUpdateDTO) {
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setBalanceGet(balanceUpdateDTO.getBalanceGet());
        balanceEntity.setBalanceMemo(balanceUpdateDTO.getBalanceMemo());
        balanceEntity.setBalanceName(balanceUpdateDTO.getBalanceName());
        balanceEntity.setBalancePercents(balanceEntity.getBalancePercents());
        balanceEntity.setBalancePhotoName(balanceUpdateDTO.getBalancePhotoName());
        balanceEntity.setMinusAsset(balanceUpdateDTO.getMinusAsset());
        balanceEntity.setPlusAsset(balanceUpdateDTO.getPlusAsset());
        balanceEntity.setDate(balanceUpdateDTO.getDate());

        return balanceEntity;
    }

    public static BalanceEntity toSaveData(BalanceSaveDTO balanceSaveDTO, CashEntity cashEntity) {
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setCashEntity(cashEntity);
        balanceEntity.setBalanceGet(balanceSaveDTO.getBalanceGet());
        balanceEntity.setBalanceName(balanceSaveDTO.getBalanceName());
        balanceEntity.setBalanceMemo(balanceSaveDTO.getBalanceMemo());
        balanceEntity.setBalancePercents(balanceSaveDTO.getBalancePercents());
        balanceEntity.setPlusAsset(balanceSaveDTO.getPlusAsset());
        balanceEntity.setMinusAsset(balanceSaveDTO.getMinusAsset());
        balanceEntity.setBalancePhotoName(balanceSaveDTO.getBalancePhotoName());
        balanceEntity.setDate(balanceSaveDTO.getDate());

        return balanceEntity;
    }
}
