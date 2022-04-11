package com.hmh.mmp.service;

import com.hmh.mmp.common.PagingConst;
import com.hmh.mmp.dto.notice.NoticeDetailDTO;
import com.hmh.mmp.dto.notice.NoticePagingDTO;
import com.hmh.mmp.dto.notice.NoticeSaveDTO;
import com.hmh.mmp.dto.notice.NoticeUpdateDTO;
import com.hmh.mmp.entity.MemberEntity;
import com.hmh.mmp.entity.NoticeEntity;
import com.hmh.mmp.repository.MemberRepository;
import com.hmh.mmp.repository.NoticeRepository;
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
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository nr;
    private final MemberRepository mr;

    @Override
    public List<NoticeDetailDTO> findAll() {
        List<NoticeEntity> noticeEntityList = nr.findAll();
        List<NoticeDetailDTO> noticeDetailDTOList = new ArrayList<>();

        for (NoticeEntity ne: noticeEntityList) {
            noticeDetailDTOList.add(NoticeDetailDTO.toDataMove(ne));
        }

        return noticeDetailDTOList;
    }

    @Override
    public Page<NoticePagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();;
        page = (page == 1) ? 0 : (page - 1);

        Page<NoticeEntity> noticeEntityPage = nr.findAll(PageRequest.of(page, PagingConst.N_PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<NoticePagingDTO> noticePagingDTOPage = noticeEntityPage.map(
                notice -> new NoticePagingDTO(notice.getId(),
                        notice.getNoticeHits(),
                        notice.getNoticeContents(),
                        notice.getNoticePassword(),
                        notice.getNoticeTitle(),
                        notice.getNoticeWriter(),
                        notice.getNoticePhotoName(),
                        notice.getMemberEntity().getId())
        );

        return noticePagingDTOPage;
    }

    @Override
    public NoticeDetailDTO findById(Long noticeId) {
        System.out.println("NoticeServiceImpl.findById");

        Optional<NoticeEntity> noticeEntityOptional = nr.findById(noticeId);
        NoticeEntity noticeEntity = noticeEntityOptional.get();

        NoticeDetailDTO noticeDetailDTO = NoticeDetailDTO.toDataMove(noticeEntity);

        return noticeDetailDTO;
    }

    @Override
    public Long save(NoticeSaveDTO noticeSaveDTO) throws IOException {
        System.out.println("NoticeServiceImpl.save");

        // 사진명 저장
        MultipartFile noticePhoto = noticeSaveDTO.getNoticePhoto();
        String noticePhotoName = noticePhoto.getOriginalFilename();
        noticePhotoName = System.currentTimeMillis() + "-" + noticePhotoName;

        String savePath = "/Users/myungha/Desktop/Github/MoneyManager_for_intel/src/main/resources/photo/notice" + noticePhotoName;

        if (!noticePhoto.isEmpty()) {
            noticePhoto.transferTo(new File(savePath));
            noticeSaveDTO.setNoticePhotoName(noticePhotoName);
        }


        // 데이터 호출
        MemberEntity memberEntity = mr.findById(noticeSaveDTO.getMemberId()).get();

        NoticeEntity noticeEntity = NoticeEntity.toSaveData(noticeSaveDTO, memberEntity);
        Long noticeId = nr.save(noticeEntity).getId();

        return noticeId;
    }

    @Override
    public Long update(NoticeUpdateDTO noticeUpdateDTO) throws IOException {
        System.out.println("NoticeServiceImpl.update");

        // 사진명 저장
        MultipartFile noticePhoto = noticeUpdateDTO.getNoticePhoto();
        String noticePhotoName = noticePhoto.getOriginalFilename();
        noticePhotoName = System.currentTimeMillis() + "-" + noticePhotoName;

        String savePath = "/Users/myungha/Desktop/Github/MoneyManager_for_intel/src/main/resources/photo/notice" + noticePhotoName;

        if (!noticePhoto.isEmpty()) {
            noticePhoto.transferTo(new File(savePath));
            noticeUpdateDTO.setNoticePhotoName(noticePhotoName);
        }

        NoticeEntity noticeEntity = NoticeEntity.toUpdateData(noticeUpdateDTO);
        Long noticeId = nr.save(noticeEntity).getId();

        return noticeId;
    }

    @Override
    public void delete(Long noticeId) {
        System.out.println("NoticeServiceImpl.delete");

        NoticeEntity noticeEntity = nr.findById(noticeId).get();
        nr.delete(noticeEntity);
    }
}
