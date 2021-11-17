package kr.ac.uc.webchatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @RequestMapping("/")
    public String root() {
        // 기본 페이지 -> 채팅 메인
        return "redirect:/chat/main";
    }

    @RequestMapping("/login")
    public String login() {
        // 로그인 페이지
        return "security/login";
    }

    @RequestMapping("/register")
    public String register() {
        // 회원가입 페이지
        return "security/register";
    }

    @RequestMapping("/admin")
    public String admin() {
        // 관리자 페이지
        return "redirect:/chat/main";
    }
}
