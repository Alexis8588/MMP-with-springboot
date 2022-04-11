package com.hmh.mmp.entity.list;

import com.hmh.mmp.entity.CreditEntity;
import com.hmh.mmp.entity.DebitEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cflist_table")
public class CardFirstListEntity { // 카드사용 대분류
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cflist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    private CreditEntity creditEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debit_id")
    private DebitEntity debitEntity;

    private String firstList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cardSecondListEntity")
    private List<CardSecondListEntity> cardSecondListEntityList = new ArrayList<>();
}
