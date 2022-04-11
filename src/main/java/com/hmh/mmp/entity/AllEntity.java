package com.hmh.mmp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "all_table")
public class AllEntity extends BaseEntity{
    // 모든 데이터를 한방에 다 저축해서 검색할때 편하게 하기 위해서 생성 (제대로 이렇게 해야하는게 맞는지 아직 판단이 안됨
    @Id

    @Column(name = "all_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 용 명령어
    private Long id;
}
