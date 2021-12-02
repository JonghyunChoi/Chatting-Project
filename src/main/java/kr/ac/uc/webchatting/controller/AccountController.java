package kr.ac.uc.webchatting.controller;

import kr.ac.uc.webchatting.dao.IUserAccountDAO;
import kr.ac.uc.webchatting.dto.UserAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    BCryptPasswordEncoder pwdEncoder;

    @Autowired
    IUserAccountDAO userAccountDAO;

    @RequestMapping("/auth_register")
    public String createAccount(Model model,
                                HttpServletRequest request,
                                @ModelAttribute("dto") @Valid UserAccountDTO userAccountDTO,
                                BindingResult result) {
        String id_errorMsg = "";
        String pwd_errorMsg = "";
        String nickname_errorMsg = "";

        if(result.hasErrors()) { // 회원가입 시 문제가 있을 경우
            if(result.getFieldError("id") != null) {
                id_errorMsg = result.getFieldError("id").getDefaultMessage();
                //System.out.println(result.getFieldError("id").getDefaultMessage());
                model.addAttribute("id_error_message", id_errorMsg);
            }

            if(result.getFieldError("password") != null) {
                pwd_errorMsg = result.getFieldError("password").getDefaultMessage();
                //System.out.println(pwd_errorMsg);
                model.addAttribute("pwd_error_message", pwd_errorMsg);
            }

            if(result.getFieldError("nickname") != null) {
                nickname_errorMsg = result.getFieldError("nickname").getDefaultMessage();
                //System.out.println(nickname_errorMsg);
                model.addAttribute("nickname_error_message", nickname_errorMsg);
            }

            return "thymeleaf/security/register";

        } else { // 회원가입 시 문제가 없을 경우
            String id = request.getParameter("id");
            List<UserAccountDTO> userIDList = userAccountDAO.getUserID(id);
            String pwd = pwdEncoder.encode(request.getParameter("password"));
            String nickname = request.getParameter("nickname");
            String auth = "ROLE_USER";
            int enabled = 1;
            String status_msg = "";

            if(userIDList.size() != 0) {
                if(userIDList.get(0).getId().equals(id)) { // 아이디가 중복일 경우
                    id_errorMsg = "중복된 아이디입니다.";
                    System.out.println(id_errorMsg);
                    model.addAttribute("id_error_message", id_errorMsg);

                    return "thymeleaf/security/register";
                }
            }

            UserAccountDTO accountDTO = new UserAccountDTO();
            accountDTO.setId(id);
            accountDTO.setPassword(pwd);
            accountDTO.setAuthority(auth);
            accountDTO.setNickname(nickname);
            accountDTO.setEnabled(enabled);
            accountDTO.setStatus_msg(status_msg);

            userAccountDAO.saveUserAccount(accountDTO);

            return "redirect:/login";
        }
    }
}









