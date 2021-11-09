package kr.ac.uc.webchatting.dto;

import kr.ac.uc.webchatting.service.ChatService;
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

    public void handleActions(WebSocketSession session, ChatDTO chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatDTO.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장 했습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}

