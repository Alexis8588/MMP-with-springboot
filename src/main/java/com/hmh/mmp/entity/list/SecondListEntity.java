package com.hmh.mmp.entity.list;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "slist_table")
public class SecondListEntity { // 계좌 & 현금 사용 중분류
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flist_id")
    private FirstListEntity firstListEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secondListEntity")
    private List<ThirdListEntity>thirdListEntityList = new ArrayList<>();

    private String secondList;
}
