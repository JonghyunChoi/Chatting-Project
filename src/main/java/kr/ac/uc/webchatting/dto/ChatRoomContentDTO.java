package kr.ac.uc.webchatting.dto;

import lombok.Data;

@Data
public class ChatRoomContentDTO {
    // 채팅방 채팅 내역
    private int room_id;            // 방 번호
    private String id;              // 유저 아이디
    private String chat_content;    // 채팅 내용
    private String chat_date;       // 채팅 보낸 날짜
    private String file_url;        // 파일 URL
    private String chat_type;       // 채팅 타입(채팅, 파일 등)
}
