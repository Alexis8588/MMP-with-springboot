package com.hmh.mmp.entity;

import com.hmh.mmp.dto.debit.DebitSaveDTO;
import com.hmh.mmp.dto.debit.DebitUpdateDTO;
import com.hmh.mmp.entity.list.CardFirstListEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "debit_entity")
public class DebitEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private CardEntity cardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "debitEntity", fetch = FetchType.LAZY)
    private List<BankEntity> bankEntityList= new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "debitEntity")
    private List<CardFirstListEntity> cardFirstListEntityList = new ArrayList<>();
    // bankName.... 이 필요한데. (정확히... 맞나?)

    private LocalDate calDate;
    private LocalDate date;
    private LocalDateTime calTime;
    private String debitName; //내역명
    private String debitPhotoName; // 영수증
    private Double minusAsset; // 지출
    private String debitMemo; // 내역
    private Double debitGet; // 할인
    private Double debitPercent; // 할인율

    private String account; // 계좌

    public static DebitEntity toSaveData(DebitSaveDTO debitSaveDTO, CardEntity cardEntity, BankEntity bankEntity) {
        DebitEntity debitEntity = new DebitEntity();
        debitEntity.setCardEntity(cardEntity);
        debitEntity.setDebitGet(debitSaveDTO.getDebitGet());
        debitEntity.setAccount(debitSaveDTO.getAccount()); // 해당 값을 List가 아니라. 정확한 하나의 값으로 정해서 줘야하는데 어떻게 줘야하는가...?
        debitEntity.setDebitMemo(debitSaveDTO.getDebitMemo());
        debitEntity.setDebitName(debitSaveDTO.getDebitName());
        debitEntity.setDebitPercent(debitSaveDTO.getDebitPercent());
        debitEntity.setDebitPhotoName(debitSaveDTO.getDebitPhotoName());
        debitEntity.setDate(debitSaveDTO.getDate());

        return debitEntity;
    }

    public static DebitEntity toUpdateData(DebitUpdateDTO debitUpdateDTO) {
        DebitEntity debitEntity = new DebitEntity();
        debitEntity.setDebitPhotoName(debitUpdateDTO.getDebitPhotoName());
        debitEntity.setDebitGet(debitUpdateDTO.getDebitGet());
        debitEntity.setAccount(debitUpdateDTO.getAccount()); // 해당 값을 List가 아니라. 정확한 하나의 값으로 정해서 줘야하는데 어떻게 줘야하는가...?
        debitEntity.setDebitMemo(debitUpdateDTO.getDebitMemo());
        debitEntity.setDebitName(debitUpdateDTO.getDebitName());
        debitEntity.setDebitPercent(debitUpdateDTO.getDebitPercent());
        debitEntity.setDate(debitUpdateDTO.getDate());

        return debitEntity;
    }
}
