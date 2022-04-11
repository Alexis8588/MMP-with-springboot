package com.hmh.mmp.entity.list;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ctlist_table")
public class CardThirdListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ctlist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardSecondListEntity cardSecondListEntity;

    private String thirdList;
}
