package kr.ac.uc.webchatting.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoomDTO {
    private int room_id;
    private String room_name;
    private String master_id;
    private int public_open;

    private Set<WebSocketSession> sessions = new HashSet<>();
}

