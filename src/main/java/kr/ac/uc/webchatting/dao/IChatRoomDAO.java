package kr.ac.uc.webchatting.dao;

import kr.ac.uc.webchatting.dto.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface IChatRoomDAO {
    public void addChatRoom(ChatRoomDTO dto);                           // 채팅방 생성
    public List<ChatRoomDTO> myRoomList(String id);                     // 자신이 참여하고 있는 채팅방 불러오기
    public List<ChatRoomDTO> publicRoomList();                          // 공개 채팅방 불러오기
    public int getChatRoomID();                                         // 최신 채팅방 번호 가져오기
    public String getChatRoomMasterID(int room_id);                         // 방장 이름 가져오기
    public String getChatRoomName(int room_id);                         // 채팅방 이름 불러오기
    public void addChatRoomTotalPeople(int people_num, int room_id);    // 채팅방 참여자 수 증가, 감소
}
