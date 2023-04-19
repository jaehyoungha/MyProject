package kopo.poly.controller;

import kopo.poly.auth.JwtTokenProvider;
import kopo.poly.dto.MailDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.service.IUserInfoService;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static kopo.poly.auth.JwtTokenType.ACCESS_TOKEN;


@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    @Value("${jwt.token.access.name}")
    private String accessTokenName;

    private final IUserService userService;

    /**
     * 회원가입
     */
    //ResponseEntity : HttpEntity 를 상속받는 클래스로, HTTP 요청 또는 응답에 해당하는 HttpHeader 와 HttpBody를 포함하는 클래스
    //ResponseEntity의 파라미터 : body – the entity body / headers – the entity headers / status – the status code
    //스프링에서 비동기 처리를 하는 경우 @RequestBody , @ResponseBody를 사용   --> 보통 JSON 형식의 데이터
    //@RequestBody : 요청 본문  /  @ResponseBody :  응답 본문
    @PostMapping(value = "insertUserInfo")
    public ResponseEntity<Integer> insertUserInfo(@RequestBody UserDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + "insertUserInfo(회원가입 시작)START");

        int res = userService.insertUserInfo(pDTO);

        return ResponseEntity.ok().body(res);   //ok :  200  정상적으로 받았으면, 바디에 res를 담아서 결과를 담고 , 다시 호출했던 쪽으로 리턴을 한다.
    }

    /**
     * 아이디 중복조회
     */
    @PostMapping(value = "checkUserId")
    public ResponseEntity<Integer> checkUserId(@RequestBody String userId) throws Exception {
        log.info(this.getClass().getName() + "userIdCheck(user_Service 아이디 중복체크 시작) START");

        int res=userService.checkUserId(userId);



        return ResponseEntity.ok().body(res);
    }

    /**
     * 닉네임 중복조회
     */
    @PostMapping(value = "checkUserNickName")
    public ResponseEntity<Integer> checkUserNickName(@RequestBody String userNickName) throws Exception {
        log.info(this.getClass().getName() + "checkUserNickName(user_Service 닉네임 중복체크 시작) START");


        int res = userService.checkUserNickName(userNickName);

        return ResponseEntity.ok().body(res);
    }


    /**
     * 이메일 중복조회
     */
    @PostMapping(value = "checkUserEmail")
    public ResponseEntity<Integer> checkUserEmail(@RequestBody String userEmail) throws Exception {
        log.info(this.getClass().getName() + "checkUserEmail(user_Service 이메일 중복체크 시작) START");


        int res = userService.checkUserEmail(userEmail);

        return ResponseEntity.ok().body(res);
    }

    /**
     * 이메일 인증 번호 발송
     */
    @PostMapping(value = "doSendmail")
    public ResponseEntity<Map> doSendmail(@RequestBody String userEmail , MailDTO mailDTO )  {
        log.info(this.getClass().getName() + "doSendmail(user_Service 이메일 인증 번호 발송 시작) START");

        int res = 0;


        Random random = new Random();
        int randomPin = random.nextInt(888888) + 111111; //인증번호

        String title = "반갑습니다. 환영합니다.";
        String contents = "(인증번호 :" + randomPin + ")";
        log.info("randomPIN: " + randomPin);


        mailDTO.setTomail(userEmail);
        mailDTO.setTitle(title);
        mailDTO.setContents(contents);
        mailDTO.setRandompin(randomPin);

        res = mailService.doSendmail(mailDTO);

        if (res == 1) {
            log.info(this.getClass().getName() + "메일 발송 성공!");
        } else {
            log.info(this.getClass().getName() + "메일 발송 실패!");
        }
//        view에서 실시간으로 res,randomPin값을 받기 위해서 Map형태로 변환
        Map<String, String> pMap = new HashMap<>();
        pMap.put("res", String.valueOf(res));
        pMap.put("randomPin", String.valueOf(randomPin));

        log.info(this.getClass().getName() + "doSendmail(user_Service 이메일 인증 번호 발송 끝) end");


        return ResponseEntity.ok().body(pMap);
    }

    /**
     * 아이디 찾기
     * */

    @PostMapping(value = "findUserId")
    public ResponseEntity<String>findUserId(@RequestBody UserInfoDTO userInfoDTO)throws Exception{
        log.info(this.getClass().getName() + "findUserId(user_Service 회원 아이디 찾기 시작) START");

        String userId= userInfoService.findUserId(userInfoDTO);

        log.info(this.getClass().getName() + "findUserId(user_Service 회원 아이디 찾기 끝) end");
        return ResponseEntity.ok().body(userId);
    }
}