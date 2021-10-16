package kr.ac.uc.webchatting.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserAccountDTO {
    @NotEmpty(message = "아이디가 공백입니다.")
    private String id;
    @NotEmpty(message = "비밀번호가 공백입니다.")
    private String password;
    private String authority;
    @NotEmpty(message = "닉네임이 공백입니다.")
    private String nickname;
    private int enabled;
}
