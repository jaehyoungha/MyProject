package kopo.poly.service;

import kopo.poly.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    //회원가입
    int insertUserInfo(UserDTO pDTO)throws Exception;

    //아이디 중복조회
    int checkUserId(String userId)throws Exception;

    //이메일 중복조회
    int checkUserEmail(String userEmail)throws Exception;

    //아이디 찾기
    String findUserId (UserDTO pDTO)throws Exception;



}
