package kr.ac.uc.webchatting.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.uc.webchatting.dto.ChatDTO;
import kr.ac.uc.webchatting.dto.ChatRoomDTO;
import kr.ac.uc.webchatting.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatDTO chatMessage = objectMapper.readValue(payload, ChatDTO.class);
        ChatRoomDTO room = chatService.findRoomById(chatMessage.getRoom_id());
        room.handleActions(session, chatMessage, chatService);
    }
}
