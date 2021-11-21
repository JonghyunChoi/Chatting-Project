package kr.ac.uc.webchatting.servlet;

import kr.ac.uc.webchatting.dao.ChatDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;

@WebServlet(name = "ChatSubmitServlet", value = "/ChatSubmitServlet")
public class ChatSubmitServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset+UTF-8");
        String fromID = request.getParameter("fromID");
        String toID = request.getParameter("toID");
        String chatContent = request.getParameter("chatContent");
        if(fromID == null || fromID.equals("") || toID == null || toID.equals("")
                || chatContent == null || chatContent.equals("")) {
            response.getWriter().write("0");
        } else{
            fromID = URLDecoder.decode(fromID, "UTF-8");
            toID = URLDecoder.decode(toID, "UTF-8");
            chatContent = URLDecoder.decode(chatContent, "UTF-8");
            response.getWriter().write(new ChatDAO().submitChat(fromID, toID, chatContent) + "");
        }
    }
}
