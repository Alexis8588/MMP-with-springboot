package com.hmh.mmp.entity;

import com.hmh.mmp.dto.credit.CreditSaveDTO;
import com.hmh.mmp.dto.credit.CreditUpdateDTO;
import com.hmh.mmp.entity.list.CardFirstListEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 카드의 신용카드 관련 정보를 저장하기 위한 것
@Entity
@Getter
@Setter
@Table(name = "credit_table")
public class CreditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private CardEntity cardEntity;

    @OneToMany(mappedBy = "creditEntity", fetch = FetchType.LAZY)
    private List<BankEntity> bankEntityList= new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creditEntity")
    private List<CardFirstListEntity> cardFirstListEntityList = new ArrayList<>();

    private LocalDate calDate;
    private LocalDate date;
    private LocalDateTime calTime;
    private String creditName; // 내역
    private String creditPhotoName; // 사진명
    private String creditMemo;
    private Double minusAsset; // 지출
    private Double rate; // 할부이자.
    private Integer month; // 할부
    private Double creditGet; // 캐쉬백
    private Double creditPercents; // 캐쉬백

    private String account; // 유니크로 설정을 해야하나?


    public static CreditEntity toSaveData(CreditSaveDTO creditSaveDTO, CardEntity cardEntity) {
        CreditEntity creditEntity = new CreditEntity();
        creditEntity.setCardEntity(cardEntity);
        creditEntity.setCreditGet(creditSaveDTO.getCreditGet());
        creditEntity.setCreditMemo(creditSaveDTO.getCreditMemo());
        creditEntity.setCreditName(creditSaveDTO.getCreditName());
        creditEntity.setCreditPercents(creditSaveDTO.getCreditPercents());
        creditEntity.setCreditPhotoName(creditSaveDTO.getCreditPhotoName());
        creditEntity.setMinusAsset(creditSaveDTO.getMinusAsset());
        creditEntity.setMonth(creditSaveDTO.getMonth());
        creditEntity.setRate(creditSaveDTO.getRate());
        creditEntity.setDate(creditSaveDTO.getDate());

        return creditEntity;
    }

    public static CreditEntity toUpdateData(CreditUpdateDTO creditUpdateDTO) {
        CreditEntity creditEntity = new CreditEntity();
        creditEntity.setCreditPhotoName(creditUpdateDTO.getCreditPhotoName());
        creditEntity.setCreditMemo(creditUpdateDTO.getCreditMemo());
        creditEntity.setCreditGet(creditUpdateDTO.getCreditGet());
        creditEntity.setCreditPercents(creditUpdateDTO.getCreditPercents());
        creditEntity.setCreditName(creditUpdateDTO.getCreditName());
        creditEntity.setMonth(creditUpdateDTO.getMonth());
        creditEntity.setMinusAsset(creditUpdateDTO.getMinusAsset());
        creditEntity.setRate(creditUpdateDTO.getRate());
        creditEntity.setDate(creditUpdateDTO.getDate());

        return creditEntity;
    }
}
