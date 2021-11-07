package kr.ac.uc.webchatting.dto;

import lombok.Data;

@Data
public class ChatRoomDTO {
    private int room_id;
    private String room_name;
    private String master_id;
    private int public_open;
}
