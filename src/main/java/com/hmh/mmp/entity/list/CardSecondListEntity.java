package com.hmh.mmp.entity.list;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "cslist_table")
public class CardSecondListEntity { // 카드 사용 중분류
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cslist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cflist_id")
    private CardSecondListEntity cardSecondListEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cardSecondListEntity")
    private List<CardThirdListEntity> cardThirdListEntityList = new ArrayList<>();

    private String secondList;

}
