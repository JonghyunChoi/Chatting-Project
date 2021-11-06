package kr.ac.uc.webchatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @RequestMapping("/main")
    public String chatMain() { return "thymeleaf/chat_Main"; }

    @RequestMapping("/list")
    public String chatRoomList() { return "thymeleaf/chat_MyRoomList"; }

    @RequestMapping("/make")
    public String chatMakeRoom() { return "thymeleaf/chat_MakeRoom"; }

    @RequestMapping("/public")
    public String chatPublicRoom() { return ""; }
}
