package com.hmh.mmp.entity.list;

import com.hmh.mmp.entity.AccountEntity;
import com.hmh.mmp.entity.BalanceEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "flist_table")
public class FirstListEntity { // 계좌 & 현금 사용 중분류
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "balance_id")
    private BalanceEntity balanceEntity;

    private String firstList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "firstListEntity")
    private List<SecondListEntity> secondListEntityList = new ArrayList<>();
}
