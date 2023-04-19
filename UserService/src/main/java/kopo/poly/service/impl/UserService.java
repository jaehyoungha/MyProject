package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.auth.AuthInfo;
import kopo.poly.dto.MailDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.repository.UserRepository;
import kopo.poly.repository.entity.UserEntity;
import kopo.poly.service.IUserInfoService;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import kopo.poly.util.RandomStringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * */
    @Override
    public int insertUserInfo(UserDTO pDTO) throws Exception {
        log.info(this.getClass().getName()+"insertUserInfo(user_Service 회원가입 ) 시작");

        int res=0;

        //회원가입을 위한 Entity 생성
        UserEntity pEntity =UserEntity.builder()
                .userId(pDTO.getUserId())
                .userPwd(pDTO.getUserPwd()).userName(pDTO.getUserName())
                .userEmail(EncryptUtil.encAES128CBC(pDTO.getUserEmail())).addr1(pDTO.getAddr1()).addr2(pDTO.getAddr2())
                //TODO 이메일 비밀번호 암호화 작업 예정(Spring Security)
                .regId(pDTO.getUserId()).regDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                .chgId(pDTO.getUserId()).chgDt(DateUtil.getDateTime("yyyy-MM--dd hh:mm:ss")).roles(pDTO.getRoles())
                .build();

        //DB에 저장하기
        userRepository.save(pEntity);

        //혹시 모르니까 한번 더 중복확인 하기
        Optional<UserEntity> rEntity = userRepository.findByUserId(pDTO.getUserId());

        //아이디가 이미 있으면 실패 res=1
        if(rEntity.isPresent()){
            res=1;
        }

        log.info(this.getClass().getName()+"insertUserInfo 종료");

        return res;
    }


    /**
     * 아이디 중복조회
     * */

    @Override
    public int checkUserId(String userId) throws Exception {
        log.info(this.getClass().getName()+"checkUserId(user_Service 아이디 중복체크) 시작 start");
        int res=0;

        Optional<UserEntity> rEntity = userRepository.findByUserId(userId);
        //아이디가 이미 있으면 실패 res=1
        if(rEntity.isPresent()){
            res=1;
        }
        log.info(this.getClass().getName()+"checkUserId (user_Service 아이디 중복체크) 종료 end");

        return res;
    }


    /**
     * 닉네임 중복 조회
     * */
    @Override
    public int checkUserNickName(String userNickName) throws Exception {
        log.info(this.getClass().getName()+"checkUserNickName(user_Service 닉네임 중복 체크) start");
        int res=0;

        Optional<UserEntity>rEntity = userRepository.findByUserNickName(userNickName);

        if(rEntity.isPresent()){
            res=1;
        }

        log.info(this.getClass().getName()+"checkUserNickName (user_Service 닉네임 중복체크) 종료 end");

        return res;
    }

    /**
     * 이메일 중복 조회
     * */

    @Override
    public int checkUserEmail(String userEmail) throws Exception {
        log.info(this.getClass().getName()+"checkUserEmail(user_Service 이메일 중복 체크) start");

        int res=0;
        Optional<UserEntity>rEntity =userRepository.findByUserEmail(userEmail);

        if(rEntity.isPresent()){
            res=1;
        }

        log.info(this.getClass().getName()+"checkUserEmail (user_Service 이메일 중복체크) 종료 end");
        return res;
    }

    /**
     * 아이디 찾기
     * */

    @Override
    public String findUserId(UserDTO pDTO) throws Exception {
        log.info(this.getClass().getName()+"findUserId(user_Service 아이디 찾기) start");

        UserEntity userInfoEntity =userRepository.findByUserNameAndUserEmail(pDTO.getUserName(), pDTO.getUserEmail());

        String userId="";

        if(userInfoEntity!=null){
            userId=userInfoEntity.getUserId();
        }

        log.info("회원 아이디는 잘 들어왔니? : "+ userId);
        log.info(this.getClass().getName()+"findUserId(user_Service 아이디 찾기)종료 end");


        return userId;
    }

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        return null;
    }

    /**
     * 비밀번호 찾기 및 변경
     * */

    //TODO 비밀번호 찾기 및 변경 작업 예정 (Spring Security)
//    @Transactional
//    @Override
//    public String findUserPwd(UserInfoDTO userInfoDTO) throws Exception {
//
//        log.info(this.getClass().getName()+"findUserPwd(user_Service 비밀번호 찾기 및 변경 시작) START");
//        int res= userInfoRepository.updateUserPwd(pa)
//
//     작성 중~~
//        log.info(this.getClass().getName()+"findUserPwd(user_Service 비밀번호 찾기 및 변경 끝) END");
//
//        return null;
//    }

    //TODO 로그인 작업 예정 (Spring Security)
//    /**
//     * 로그인
//     * */
//    @Override
//    public int getUserLogin(UserInfoDTO userInfoDTO) throws Exception {
//        log.info(this.getClass().getName()+"getUserLogin(user_Service 회원 로그인 시작) start");
//        int res=0;
//
//        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserIdAndUserPwd(userInfoDTO.getUserId(), userInfoDTO.getUserPwd());
//
//        if(rEntity.isPresent()){
//            res=1;
//        }
//        log.info(this.getClass().getName()+"getUserLogin(user_Service 회원 로그인 끝) end");
//        return res;
//    }

}
