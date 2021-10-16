package kr.ac.uc.webchatting.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
    throws IOException, ServletException {
        String id = request.getParameter("id");
        String errorMsg = "";

        if(exception instanceof BadCredentialsException) {
            loginFailureCount(id);
            errorMsg = "아이디나 비밀번호가 맞지 않습니다.";
        } else if(exception instanceof InternalAuthenticationServiceException) {
            loginFailureCount(id);
            errorMsg = "아이디나 비밀번호가 맞지 않습니다.";
        } else if(exception instanceof DisabledException) {
            errorMsg = "계정이 비활성화되었습니다. 관리자에게 문의하세요.";
        } else if(exception instanceof CredentialsExpiredException) {
            errorMsg = "비밀번호 유효기간이 만료되었습니다. 관리자에게 문의하세요.";
        }

        request.setAttribute("id", id);
        request.setAttribute("error_message", errorMsg);
        request.getRequestDispatcher("/login?error=true").forward(request, response);
    }

    protected void loginFailureCount(String id) {
        /*

        */
    }
}
