package kr.ac.uc.webchatting.dao;

import kr.ac.uc.webchatting.dto.ChatDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChatDAO {
    DataSource dataSource;

    public ChatDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/UserChat");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID) {
        ArrayList<ChatDTO> chatList = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * FROM CHAT WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > ? ORDER BY chatTime";
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, fromID);
            pstmt.setString(2, toID);
            pstmt.setString(3, toID);
            pstmt.setString(4, fromID);
            pstmt.setInt(5, Integer.parseInt(chatID));
            rs = pstmt.executeQuery();
            chatList = new ArrayList<ChatDTO>();
            while (rs.next()) {
                ChatDTO chat = new ChatDTO();
                chat.setChatID(rs.getInt("chatID"));
                chat.setFromID(rs.getString("fromID").replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
                chat.setToID(rs.getString("fromID").replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
                chat.setChatContent(rs.getString("fromID").replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
                int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
                String timeType = "AM";
                if(chatTime >= 12) {
                    timeType = "PM";
                    chatTime -= 12;
                }if (chatTime == 12){
                    timeType = "PM";
                }
                chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
                chatList.add(chat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  chatList;
    }

    public ArrayList<ChatDTO> getChatListByResent(String fromID, String toID, int number) {
        ArrayList<ChatDTO> chatList = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * FROM CHAT WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > (SELECT MAX(chatID) - ? FROM CHAT) ORDER BY chatTime";
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, fromID);
            pstmt.setString(2, toID);
            pstmt.setString(3, toID);
            pstmt.setString(4, fromID);
            pstmt.setInt(5, number);
            rs = pstmt.executeQuery();
            chatList = new ArrayList<ChatDTO>();
            while (rs.next()) {
                ChatDTO chat = new ChatDTO();
                chat.setChatID(rs.getInt("chatID"));
                chat.setFromID(rs.getString("fromID").replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
                chat.setToID(rs.getString("fromID").replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
                chat.setChatContent(rs.getString("fromID").replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
                int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
                String timeType = "AM";
                if(chatTime >= 12) {
                    timeType = "PM";
                    chatTime -= 12;
                }if (chatTime == 12){
                    timeType = "PM";
                }
                chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
                chatList.add(chat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  chatList;
    }

    public int submitChat(String fromID, String toID, String chatContent) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "INSERT INTO CHAT VALUES (NULL, ?, ?, ?, NOW())";
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, fromID);
            pstmt.setString(2, toID);
            pstmt.setString(3, chatContent);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  -1;
    }
}
