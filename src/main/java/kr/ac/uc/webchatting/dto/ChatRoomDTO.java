package kr.ac.uc.webchatting.dto;

import lombok.Data;

@Data
public class ChatRoomDTO {
    // 채팅방 정보
    private int room_id;
    private String room_name;
    private String master_id;
    private int total_people;
    private int public_open;

}

