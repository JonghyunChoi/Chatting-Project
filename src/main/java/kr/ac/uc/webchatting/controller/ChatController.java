package kr.ac.uc.webchatting.controller;

import kr.ac.uc.webchatting.auth.MyDetails;
import kr.ac.uc.webchatting.dao.IChatRoomContentDAO;
import kr.ac.uc.webchatting.dao.IChatRoomDAO;
import kr.ac.uc.webchatting.dao.IChatRoomUserInfoDAO;
import kr.ac.uc.webchatting.dto.ChatRoomContentDTO;
import kr.ac.uc.webchatting.dto.ChatRoomDTO;
import kr.ac.uc.webchatting.dto.ChatRoomUserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    IChatRoomDAO chatRoomDAO;
    @Autowired
    IChatRoomUserInfoDAO chatRoomUserInfoDAO;
    @Autowired
    IChatRoomContentDAO chatRoomContentDAO;


    public void menuActive(int active, Model model) {
        /* 사이드바 메뉴 CSS 활성화 로직 */

        int _active = active;
        model.addAttribute("active", _active);
    }

    public void sendMyInfoData(@AuthenticationPrincipal MyDetails myDetails, Model model) {
        /* 유저 데이터 전달 로직 */
        
        String id = myDetails.getUsername();
        String nickname = myDetails.getUserNickname();

        model.addAttribute("id", id);
        model.addAttribute("nickname", nickname);
    }

    @RequestMapping("/main")
    public String chatMain(@AuthenticationPrincipal MyDetails myDetails, Model model) {
        /* 메인 페이지 */

        // 전달할 데이터
        menuActive(0, model);
        sendMyInfoData(myDetails, model);

        return "thymeleaf/chat_Main";
    }

    @RequestMapping("/list")
    public String chatRoomList(@AuthenticationPrincipal MyDetails myDetails, Model model) {
        /* 참여하고 있는 채팅방 리스트 페이지 */
        String id = myDetails.getUsername(); // 유저 아이디
        
        // 전달할 데이터
        sendMyInfoData(myDetails, model);
        List<ChatRoomDTO> myRoomInfoCarrier = chatRoomDAO.myRoomList(id);
        model.addAttribute("myRoomInfoCarrier", myRoomInfoCarrier);
        menuActive(1, model);

        return "thymeleaf/chat_MyRoomList";
    }

    @RequestMapping("/make")
    public String chatRoomMakePage(@AuthenticationPrincipal MyDetails myDetails, Model model) {
        /* 채팅방 만드는 페이지 */

        // 전달할 데이터
        sendMyInfoData(myDetails, model);
        return "thymeleaf/chat_MakeRoom";
    }

    @RequestMapping("/public")
    public String chatRoomPublic(@AuthenticationPrincipal MyDetails myDetails, Model model) {
        /* 공개 채팅방 리스트 페이지 */

        // 전달할 데이터
        sendMyInfoData(myDetails, model);
        List<ChatRoomDTO> roomInfoCarrier = chatRoomDAO.publicRoomList();
        model.addAttribute("roomInfoCarrier", roomInfoCarrier);
        menuActive(2, model);

        return "thymeleaf/chat_PublicRoomList";
    }

    @RequestMapping("/makeRoom")
    public String chatRoomMakeProcess(HttpServletRequest request, Model model) {
        /* 채팅방 생성 로직 */
        
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
        chatRoomDAO.addChatRoom(chatRoomDTO);

        // 채팅방 유저 정보 추가(생성한 유저)
        ChatRoomUserInfoDTO chatRoomUserInfoDTO = new ChatRoomUserInfoDTO();
        chatRoomUserInfoDTO.setRoom_id(chatRoomDAO.getChatRoomID());
        chatRoomUserInfoDTO.setId(master_id);
        chatRoomUserInfoDTO.setAuthority("ADMIN");
        chatRoomUserInfoDAO.insertChatRoomUserInfo(chatRoomUserInfoDTO);

        return "redirect:/chat/list";
    }

    @RequestMapping("/room/{room_id}")
    public String chatRoomEnter(@PathVariable("room_id") int room_id,
                                @AuthenticationPrincipal MyDetails myDetails, Model model) {
        /* 각 채팅방으로 이동 로직 */
        String id = myDetails.getUsername();
        String room_name = chatRoomDAO.getChatRoomName(room_id);

        // 전달할 데이터
        model.addAttribute("id", id);
        model.addAttribute("room_id", room_id);
        model.addAttribute("room_name", room_name);

        return "thymeleaf/chat_Room";
    }

    @RequestMapping(value = "/submit_message", method = RequestMethod.POST)
    public void submitMessage(@RequestBody ChatRoomContentDTO chatRoomContentDTO) {
        int room_id = chatRoomContentDTO.getRoom_id();
        String id = chatRoomContentDTO.getId();
        String chat_content = chatRoomContentDTO.getChat_content();
        String file_url = chatRoomContentDTO.getFile_url();
        String chat_type = chatRoomContentDTO.getChat_type();

        System.out.println(chatRoomContentDTO);
//        System.out.println("room_id: " + room_id);
//        System.out.println("id: " + id);
//        System.out.println("chat_content: " + chat_content);
//        System.out.println("file_url: " + file_url);
//        System.out.println("chat_type: " + chat_type);

        chatRoomContentDAO.addChatMessage(chatRoomContentDTO);
    }
}
