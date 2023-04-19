package kopo.poly.service;

import kopo.poly.dto.UserDTO;

public interface P_IUserService {


    //회원가입
    int insertUserInfo(UserDTO pDTO)throws Exception;

    //아이디 중복체크
    int checkUserId(String userId)throws Exception;

    //이메일 중복체크
    int checkUserEmail(String userEmail)throws Exception;

    //아이디 찾기
    String findUserId(UserDTO pDTO)throws Exception;

}
