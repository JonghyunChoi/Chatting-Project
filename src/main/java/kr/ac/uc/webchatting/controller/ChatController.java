package kr.ac.uc.webchatting.controller;

import kr.ac.uc.webchatting.auth.MyDetails;
import kr.ac.uc.webchatting.dto.UserAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Security;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @RequestMapping("/main")
    public String chatMain() {
        return "thymeleaf/chat_Main";
    }

    @RequestMapping("/list")
    public String chatRoomList() { return "thymeleaf/chat_MyRoomList"; }

    @RequestMapping("/make")
    public String chatMakeRoom(@AuthenticationPrincipal MyDetails myDetails, Model model) {
        String nickname = myDetails.getUserNickname();
        model.addAttribute("nickname", nickname);

        return "thymeleaf/chat_MakeRoom";
    }

    @RequestMapping("/public")
    public String chatPublicRoom() { return ""; }
}
