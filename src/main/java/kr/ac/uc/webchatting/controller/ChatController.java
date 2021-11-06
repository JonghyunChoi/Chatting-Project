package kr.ac.uc.webchatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat_room")
public class ChatController {
    @RequestMapping("/main")
    public String chat_room_main() { return "thymeleaf/chat_main"; }

    @RequestMapping("/list")
    public String chat_room_list() { return "thymeleaf/room_list"; }

    @RequestMapping("/make")
    public String chat_room_make() { return "thymeleaf/make_room"; }

    @RequestMapping("/public")
    public String chat_room_public() { return ""; }
}
