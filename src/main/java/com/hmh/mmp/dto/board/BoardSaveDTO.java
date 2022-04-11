package com.hmh.mmp.dto.board;

import com.hmh.mmp.dto.member.MemberDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveDTO {
    private Long memberId;
    private String boardPhotoName;
    private MultipartFile boardPhoto;
    private String boardWriter;
    private String boardContents;
    private String boardTitle;
    private String boardPassword;
    private int boardHits;

    public static BoardSaveDTO toMoveDate(MemberDetailDTO memberDetailDTO) {
        BoardSaveDTO boardSaveDTO = new BoardSaveDTO();
        boardSaveDTO.setMemberId(memberDetailDTO.getMemberId());
        boardSaveDTO.setBoardWriter(memberDetailDTO.getMemberNickName());

        return boardSaveDTO;
    }
}
