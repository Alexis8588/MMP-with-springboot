package com.hmh.mmp.entity.list;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "bflist_table")
public class BoardFirstListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bflist_id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boardFirstListEntity")
    private List<BoardSecondListEntity> boardSecondListEntityList = new ArrayList<>();

    private String firstList;
}
