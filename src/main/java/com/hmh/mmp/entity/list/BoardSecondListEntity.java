package com.hmh.mmp.entity.list;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "bslist_table")
public class BoardSecondListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bslist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bflist_id")
    private BoardFirstListEntity boardFirstListEntity;

    private String secondList;
}
