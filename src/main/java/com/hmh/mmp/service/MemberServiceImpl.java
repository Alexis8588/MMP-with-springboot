package com.hmh.mmp.service;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.member.MemberDetailDTO;
import com.hmh.mmp.dto.member.MemberLoginDTO;
import com.hmh.mmp.dto.member.MemberPagingDTO;
import com.hmh.mmp.dto.member.MemberSaveDTO;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) throws IOException {
        // 사진 관련 정보를 넣기 위한 것.
        MultipartFile memberPhoto = memberSaveDTO.getMemberPhoto();
        String memberPhotoName = memberPhoto.getOriginalFilename();

        memberPhotoName = System.currentTimeMillis() + "-" + memberPhotoName;

        String savePath = "D:\\GitHub\\MoneyManager_for_intel\\src\\main\\resources\\photo\\member" + memberPhotoName; // 미정 후에 전부 교체 해야함.

        if (!memberPhoto.isEmpty()) {
            memberPhoto.transferTo(new File(savePath));
        }

        memberSaveDTO.setMemberPhotoName(memberPhotoName);

        /*
            1. MemberSaveDTO -> MemberEntity에 옮기기 : msDTO는 더 이상 다음단계에서는 쓰지 않음. 해당 데이터는 Entity에서 다룸
                - 여기서는 MemberEntity의 saveMember 메서드
            2. MemberRepository의 save 메서드 호출하면서 MemberEntity 객체 전달

         */
        MemberEntity mEntity = MemberEntity.saveMember(memberSaveDTO); // 이것을 통해 가입 진행됨

        // 이메일 체크
        MemberEntity emailCheck = mr.findByMemberEmail(memberSaveDTO.getMemberEmail()); // 지금 설정하는게 아님...

        if (emailCheck != null) {
            throw new IllegalStateException("중복된 이메일입니다.");
        } else {
            return mr.save(mEntity).getId();
        }

    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        // MemberEntity -> MemberLogin
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail()); // 왜냐면 로그인에서 쓴 데이터는 memberLogin에서 받았기 때문에.

        if (memberEntity != null) {
            if (memberLoginDTO.getMemberPassword().equals(memberEntity.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntity = mr.findAll(); // Jpa의 findAll이란 명령어를 통해서 모든 데이터를 MemberEntity에 담는다.
        List<MemberDetailDTO> memberDetailDTOList = new ArrayList<>();
        for (MemberEntity m : memberEntity) {
            memberDetailDTOList.add(MemberDetailDTO.toMemberDetailDTO(m)); // MemberDetailDTO 에 static 정의해준 메서드에 해당 entity의 데이터 담는다.
        }

        return memberDetailDTOList;
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        // Entity(Repo)로 부터 데이터 를 가지고 와야함.
        Optional<MemberEntity> member = mr.findById(memberId); // 데이터 찾아서 가지고 옴

        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(member.get());

        return memberDetailDTO;
//        return MemberDetailDTO.toMemberDetailDTO(mr.findById(memberId).get());
    }

    // session의 데이터를 가지고 모든 데이터 또는 부분 데이터를 가지고 오기 위한 메서드.
    @Override
    public MemberDetailDTO findByEmail(String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);

        return memberDetailDTO;
    }

    @Override
    public Long update(MemberDetailDTO memberDetailDTO) {
        MemberEntity memberEntity = MemberEntity.updateMember(memberDetailDTO);
        Long memberId = mr.save(memberEntity).getId(); // controller에서 페이지 호출을 하기 위해서 필요...
        return memberId;
    }

    @Override
    public Page<MemberPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();

        page = (page == 1) ? 0 : (page - 1);

        Page<MemberEntity> mEntityPage = mr.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

        Page<MemberPagingDTO> mPageList = mEntityPage.map(
                member -> new MemberPagingDTO(member.getId(),
                        member.getMemberEmail(),
                        member.getMemberPassword(),
                        member.getMemberName(),
                        member.getMemberNickName(),
                        member.getMemberPhone(),
                        member.getMemberMemo(),
                        member.getMemberPhotoName())
        );

        return mPageList;
    }

    @Override
    public void delete(Long memberId) {
        MemberEntity memberEntity = mr.findById(memberId).get();
        mr.delete(memberEntity);
    }
}
