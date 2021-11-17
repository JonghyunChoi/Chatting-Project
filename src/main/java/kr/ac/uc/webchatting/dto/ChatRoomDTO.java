package kr.ac.uc.webchatting.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoomDTO {
    // 채팅방 정보
    private int room_id;
    private String room_name;
    private String master_id;
    private int total_people;
    private int public_open;

    private Set<WebSocketSession> sessions = new HashSet<>();
}

