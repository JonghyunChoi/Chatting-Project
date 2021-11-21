package kr.ac.uc.webchatting.dto;

import lombok.Data;

@Data
public class ChatDTO {
    int chatID;
    String fromID;
    String toID;
    String chatContent;
    String chatTime;
}
