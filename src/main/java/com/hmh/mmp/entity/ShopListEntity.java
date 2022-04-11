package com.hmh.mmp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "shoppinglist_table")
public class ShopListEntity extends BaseEntity{
    @Id
    @Column(name = "shoplist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private ShopEntity shopEntity;
}
