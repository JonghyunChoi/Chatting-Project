package kr.ac.uc.webchatting.controller;

import kr.ac.uc.webchatting.auth.MyDetails;
import kr.ac.uc.webchatting.dao.IChatRoomDAO;
import kr.ac.uc.webchatting.dto.ChatRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/chat")
public class ChatController {

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
    public String chatRoomPublic() { return ""; }

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
}
