package kr.ac.uc.webchatting.controller;

import kr.ac.uc.webchatting.auth.MyDetails;
import kr.ac.uc.webchatting.dao.IChatRoomDAO;
import kr.ac.uc.webchatting.dto.ChatDTO;
import kr.ac.uc.webchatting.dto.ChatRoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    IChatRoomDAO chatRoomDAO;

    @RequestMapping("/main")
    public String chatMain() {
        return "thymeleaf/chat_Main";
    }

    @RequestMapping("/list")
    public String chatRoomList() { return "thymeleaf/chat_MyRoomList"; }

    @RequestMapping("/make")
    public String chatRoomMakePage(@AuthenticationPrincipal MyDetails myDetails, Model model) {
        // 방장 닉네임 고정
        String id = myDetails.getUsername();
        String nickname = myDetails.getUserNickname();
        model.addAttribute("id", id);
        model.addAttribute("nickname", nickname);

        return "thymeleaf/chat_MakeRoom";
    }

    @RequestMapping("/public")
    public String chatRoomPublic(Model model) {
        List<ChatRoomDTO> roomInfoCarrier = chatRoomDAO.publicRoomList();
        model.addAttribute("roomInfoCarrier", roomInfoCarrier);

        return "thymeleaf/chat_PublicRoomList";
    }

    @RequestMapping("/makeRoom")
    public String chatRoomMakeProcess(HttpServletRequest request, Model model) {
        String room_name = request.getParameter("room_name");
        String master_id = request.getParameter("master_id");
        int public_open = Integer.parseInt(request.getParameter("public_open"));

        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setRoom_name(room_name);
        chatRoomDTO.setMaster_id(master_id);
        chatRoomDTO.setPublic_open(public_open);

        chatRoomDAO.saveChatRoom(chatRoomDTO);
        return "redirect:/chat/list";
    }

    @MessageMapping("/chat/message")
    public void message(ChatDTO message) {
        if (ChatDTO.MessageType.ENTER.equals(message.getType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoom_id(), message);
    }
}
