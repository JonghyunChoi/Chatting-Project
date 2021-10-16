package kr.ac.uc.webchatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String root() { return "redirect:/chatting"; }

    @RequestMapping("/login")
    public String login() { return "security/login"; }

    @RequestMapping("/register")
    public String register() { return "security/register"; }

    @RequestMapping("/room_list")
    public String room_list() { return "chat/room_list"; }
}
