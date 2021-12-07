package kr.ac.uc.webchatting.dto;
import lombok.Data;

@Data
public class ChatRoomDTO {
    // 채팅방 정보

    private int room_id;        // 채팅방 번호
    private String room_name;   // 채팅방 이름
    private int total_people;   // 총 인원
    private int public_open;    // 채팅방 공개 여부(0 or 1)
}
