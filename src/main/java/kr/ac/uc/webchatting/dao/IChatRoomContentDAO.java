package kr.ac.uc.webchatting.dao;

import kr.ac.uc.webchatting.dto.ChatRoomContentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IChatRoomContentDAO {
    public void addChatMessage(ChatRoomContentDTO dto);
}
