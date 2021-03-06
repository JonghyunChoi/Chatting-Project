package kr.ac.uc.webchatting.dto;

import lombok.Data;

@Data
public class ChatRoomUserInfoDTO {
    // 채팅방 유저 정보(누가 어떤 방에 있는지 등)

    private int room_id;            // 방 번호
    private String id;              // 유저 아이디

    // not DB save
    private String nickname;        // 유저 닉네임
    // *****
}
