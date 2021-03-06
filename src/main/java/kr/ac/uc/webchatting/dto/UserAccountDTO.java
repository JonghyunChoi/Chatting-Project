package kr.ac.uc.webchatting.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class UserAccountDTO {
    // 유저 계정 데이터

    @NotEmpty(message = "아이디를 입력해 주세요.")
    private String id;
    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;
    private String authority;
    @NotEmpty(message = "닉네임을 입력해 주세요.")
    private String nickname;
    private int enabled;
    private String status_msg;
}
