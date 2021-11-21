package kr.ac.uc.webchatting.controller;

import com.google.gson.Gson;
import kr.ac.uc.webchatting.auth.MyDetails;
import kr.ac.uc.webchatting.dao.IChatRoomDAO;
import kr.ac.uc.webchatting.dao.IChatRoomUserInfoDAO;
import kr.ac.uc.webchatting.dto.ChatRoomDTO;
import kr.ac.uc.webchatting.dto.ChatRoomUserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    IChatRoomDAO chatRoomDAO;
    @Autowired
    IChatRoomUserInfoDAO chatRoomUserInfoDAO;
    // SimpMessageSendingOperations messagingTemplate;

    public void menuActive(int active, Model model) {
        int _active = active;
        model.addAttribute("active", _active);
    }

    @RequestMapping("/main")
    public String chatMain(Model model) {
        // 메인 화면
        menuActive(0, model);
        return "thymeleaf/chat_Main";
    }

    @RequestMapping("/list")
    public String chatRoomList(@AuthenticationPrincipal MyDetails myDetails, Model model) {
        // 참여하고 있는 채팅방 목록
        String id = myDetails.getUsername();
        List<ChatRoomDTO> myRoomInfoCarrier = chatRoomDAO.myRoomList(id);
        model.addAttribute("myRoomInfoCarrier", myRoomInfoCarrier);
        menuActive(1, model);

        return "thymeleaf/chat_MyRoomList";
    }

    @RequestMapping("/make")
    public String chatRoomMakePage(@AuthenticationPrincipal MyDetails myDetails, Model model) {
        // 채팅방 만드는 페이지
        String id = myDetails.getUsername();
        String nickname = myDetails.getUserNickname();
        model.addAttribute("id", id);
        model.addAttribute("nickname", nickname);

        return "thymeleaf/chat_MakeRoom";
    }

    @RequestMapping("/public")
    public String chatRoomPublic(Model model) {
        // 공개 채팅방 리스트
        List<ChatRoomDTO> roomInfoCarrier = chatRoomDAO.publicRoomList();
        model.addAttribute("roomInfoCarrier", roomInfoCarrier);
        menuActive(2, model);

        return "thymeleaf/chat_PublicRoomList";
    }

    @RequestMapping("/makeRoom")
    public String chatRoomMakeProcess(HttpServletRequest request, Model model) {
        // 채팅방 생성 URL
        String room_name = request.getParameter("room_name"); // 방 이름
        String master_id = request.getParameter("master_id"); // 유저 아이디
        int total_people = 1;
        int public_open = Integer.parseInt(request.getParameter("public_open")); // 방 공개 여부

        // 방 생성
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setRoom_name(room_name);
        chatRoomDTO.setMaster_id(master_id);
        chatRoomDTO.setTotal_people(total_people);
        chatRoomDTO.setPublic_open(public_open);
        chatRoomDAO.insertChatRoom(chatRoomDTO);

        // 채팅방 유저 정보 추가(생성한 유저)
        ChatRoomUserInfoDTO chatRoomUserInfoDTO = new ChatRoomUserInfoDTO();
        chatRoomUserInfoDTO.setRoom_id(chatRoomDAO.getChatRoomID());
        chatRoomUserInfoDTO.setId(master_id);
        chatRoomUserInfoDTO.setAuthority("ADMIN");
        chatRoomUserInfoDAO.insertChatRoomUserInfo(chatRoomUserInfoDTO);

        return "redirect:/chat/list";
    }

    @RequestMapping("/room/{room_id}")
    public String chatRoomEnter(@PathVariable("room_id") int room_id, Model model) {
        // 채팅방으로 이동
        model.addAttribute("room_id", room_id);

        return "chat/chat_Room";
    }
}
