package com.hmh.mmp.entity;

import com.hmh.mmp.dto.card.CardSaveDTO;
import com.hmh.mmp.dto.card.CardUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "card_table")
public class CardEntity extends BaseEntity {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 50)
    private String cardName;
    private String cardMemo;
    private String cardPhotoName;
    private Integer level;
    private String cardTag;

    private Double totalAsset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "cardEntity", fetch = FetchType.LAZY)
    private List<DebitEntity> debitEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "cardEntity", fetch = FetchType.LAZY)
    private List<CreditEntity> creditEntityList = new ArrayList<>();

    public static CardEntity toSaveData(CardSaveDTO cardSaveDTO, MemberEntity memberEntity) {
        CardEntity cardEntity = new CardEntity();
        cardEntity.setCardName(cardSaveDTO.getCardName());
        cardEntity.setCardMemo(cardSaveDTO.getCardMemo());
        cardEntity.setTotalAsset(cardSaveDTO.getTotalAsset());
        cardEntity.setCardPhotoName(cardSaveDTO.getCardPhotoName());
        cardEntity.setMemberEntity(memberEntity);
        cardEntity.setCardTag(cardSaveDTO.getCardTag());

        return cardEntity;
    }

    public static CardEntity toUpdateData(CardUpdateDTO cardUpdateDTO) {
        CardEntity cardEntity = new CardEntity();
        cardEntity.setCardTag(cardUpdateDTO.getCardTag()); // 이거 어떻게 설정해야하는지 셋업 필요
        cardEntity.setCardName(cardUpdateDTO.getCardName());
        cardEntity.setCardMemo(cardUpdateDTO.getCardMemo());
        cardEntity.setTotalAsset(cardUpdateDTO.getTotalAsset());
        cardEntity.setCardPhotoName(cardUpdateDTO.getCardPhotoName());

        return cardEntity;
    }
}
