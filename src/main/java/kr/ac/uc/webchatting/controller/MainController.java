package kr.ac.uc.webchatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() { return "index"; }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() { return "security/login"; }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() { return "security/register"; }

//    @RequestMapping(value = "/room_list", method = RequestMethod.GET)
//    public String room_list() { return "/room_list"; }
}
