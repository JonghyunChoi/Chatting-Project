package kr.ac.uc.webchatting.controller;

import kr.ac.uc.webchatting.auth.MyDetails;
import kr.ac.uc.webchatting.dao.IChatRoomContentDAO;
import kr.ac.uc.webchatting.dao.IChatRoomDAO;
import kr.ac.uc.webchatting.dao.IChatRoomUserInfoDAO;
import kr.ac.uc.webchatting.dao.IUserAccountDAO;
import kr.ac.uc.webchatting.dto.ChatRoomContentDTO;
import kr.ac.uc.webchatting.dto.ChatRoomDTO;
import kr.ac.uc.webchatting.dto.ChatRoomUserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    IUserAccountDAO userAccountDAO;
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
        String nickname = userAccountDAO.getUserNickname(id);

        model.addAttribute("id", id);
        model.addAttribute("nickname", nickname);
    }

    public void addChatRoomUserInfo(int room_id, String id, String authority) {
        /* 채팅방 유저 정보 저장 */

        ChatRoomUserInfoDTO chatRoomUserInfoDTO = new ChatRoomUserInfoDTO();
        chatRoomUserInfoDTO.setRoom_id(room_id);
        chatRoomUserInfoDTO.setId(id);
        chatRoomUserInfoDTO.setAuthority(authority);

        chatRoomUserInfoDAO.addChatRoomUserInfo(chatRoomUserInfoDTO);
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
        
        // 전달할 데이터
        sendMyInfoData(myDetails, model);
        List<ChatRoomDTO> myRoomInfoCarrier = chatRoomDAO.myRoomList(myDetails.getUsername());
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
        List<ChatRoomDTO> myRoomInfoCarrier = chatRoomDAO.myRoomList(myDetails.getUsername());
        List<ChatRoomDTO> roomInfoCarrier = chatRoomDAO.publicRoomList();
        model.addAttribute("roomInfoCarrier", roomInfoCarrier);
        model.addAttribute("myRoomInfoCarrier", myRoomInfoCarrier);
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
        chatRoomUserInfoDAO.addChatRoomUserInfo(chatRoomUserInfoDTO);

        return "redirect:/chat/list";
    }

    @RequestMapping("/account_setting")
    public String accountSetting(@AuthenticationPrincipal MyDetails myDetails, Model model) {
        /* 유저 계정 설정 */

        sendMyInfoData(myDetails, model);

        return "thymeleaf/chat_AccountSetting";
    }

    @RequestMapping("/updateAccount")
    public String updateAccount(HttpServletRequest request, RedirectAttributes rAttr) {
        /* 계정 정보 수정 로직 */

        String id = request.getParameter("id");
        String nickname = request.getParameter("nickname");

        if(nickname == "") {
            String error_msg = "닉네임을 입력해 주세요.";
            rAttr.addFlashAttribute("error_msg", error_msg);

            return "redirect:/chat/account_setting";
        }

        userAccountDAO.updateUserAccount(id, nickname);

        return "redirect:/chat/main";
    }

    @RequestMapping("/room/{room_id}")
    public String chatRoomEnter(@PathVariable("room_id") int room_id,
                                @AuthenticationPrincipal MyDetails myDetails, Model model) {
        /* 각 채팅방으로 이동 로직 */

        String id = myDetails.getUsername();
        String room_name = chatRoomDAO.getChatRoomName(room_id);
        List<ChatRoomUserInfoDTO> user_list = chatRoomUserInfoDAO.selectMemberList(Integer.toString(room_id));
        LocalDateTime korNow = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String formattedKorNow = korNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ChatRoomContentDTO dto = new ChatRoomContentDTO();


        // 전달할 데이터
        model.addAttribute("id", id);
        model.addAttribute("room_id", room_id);
        model.addAttribute("room_name", room_name);
        model.addAttribute("list", user_list);

        if(chatRoomUserInfoDAO.checkUserInChatRoom(Integer.toString(room_id), id) == null) { // 새로운 멤버일 경우 DB 저장
            addChatRoomUserInfo(room_id, id, "USER");
            chatRoomDAO.addChatRoomTotalPeople(1, room_id);
            dto.setRoom_id(room_id);
            dto.setId(id);
            dto.setChat_content("님이 입장하셨습니다.");
            dto.setChat_date(formattedKorNow);
            dto.setFile_url("");
            dto.setChat_type("notice");

            chatRoomContentDAO.addChatMessage(dto);
        }

        return "thymeleaf/chat_Room";
    }

    @ResponseBody
    @RequestMapping(value = "/submit_message", method = RequestMethod.POST)
    public void submitMessage(@RequestBody ChatRoomContentDTO chatRoomContentDTO) {
        /* 보낸 메시지 json 파싱 및 DB 저장 로직 */

        LocalDateTime korNow = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String formattedKorNow = korNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ChatRoomContentDTO dto = new ChatRoomContentDTO();

        int room_id = chatRoomContentDTO.getRoom_id();
        String id = chatRoomContentDTO.getId();
        String chat_content = chatRoomContentDTO.getChat_content();
        String chat_date = formattedKorNow;
        String file_url = chatRoomContentDTO.getFile_url();
        String chat_type = chatRoomContentDTO.getChat_type();

        dto.setRoom_id(room_id);
        dto.setId(id);
        dto.setChat_content(chat_content);
        dto.setChat_date(chat_date);
        dto.setFile_url(file_url);
        dto.setChat_type(chat_type);

        chatRoomContentDAO.addChatMessage(dto); // DB 저장
    }

    @ResponseBody
    @RequestMapping(value = "/load_message", method = RequestMethod.POST)
    public List<ChatRoomContentDTO> loadMessage(@RequestBody ChatRoomContentDTO chatRoomContentDTO) {
        /* 채팅방 접속 시 json 형태로 ajax 로 보내는 로직 */

        int room_id = chatRoomContentDTO.getRoom_id();
        List<ChatRoomContentDTO> dtoList = chatRoomContentDAO.getChatLog(room_id);

        return dtoList;
    }

    @ResponseBody
    @RequestMapping(value = "/load_member", method = RequestMethod.POST)
    public List<ChatRoomUserInfoDTO> loadMemberList(@RequestBody ChatRoomUserInfoDTO chatRoomUserInfoDTO) {
        /* 채팅방 접속 시 json 형태로 ajax 로 보내는 로직 */

        int room_id = chatRoomUserInfoDTO.getRoom_id();
        List<ChatRoomUserInfoDTO> dto = chatRoomUserInfoDAO.selectMemberList(Integer.toString(room_id));

        return dto;
    }
}
