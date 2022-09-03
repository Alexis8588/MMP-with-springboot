package com.hmh.mmp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // 쇼핑몰 관련 정보 저장되는 것
@Getter
@Setter
@Table(name = "shop-table")
public class ShopEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shopEntity")
    private List<ShopListEntity> shopListEntityList = new ArrayList<>();
}
