package kopo.poly.controller;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class P_UserController {

    private String title="";
    private String state="";
    private String msg="";
    private String url="";
    private String sweetalert="/sweetalert";

    private final P_IUserService userInfoService;

    /**
     * 회원가입
     * */

    @GetMapping("insertUserInfo")
    public String insertUserInfo(){
        log.info(this.getClass().getName()+"insertUserInfo(page_Service 회원가입 페이지)시작 ");
        return "/user/EGO_LOGIN_002";
    }

    /**
     * 회원가입 로직
     * */
    @PostMapping("insertUserInfoProc")
    public String insertUserInfoProc(UserInfoDTO userInfoDTO, Model model)throws Exception{
        log.info(this.getClass().getName()+"insertUserInfoProc(page_Service 회원가입 로직) 시작");

        //권한 부여
        userInfoDTO.setRoles("ROLE_USER");

        int res= userInfoService.insertUserInfo(userInfoDTO);

        log.info("insertUserInfo (회원가입 로직) 확인 :" +res);

        if (res == 1) {
            title="회원가입";
            state="success";
            msg="회원가입 성공! 로그인 해주세요 ";
            url="/user/userLogin";   //controller 주소ㅓ 작성

        }else{
            title="회원가입 실패";
            state="false";
            msg="회원가입 실패! 다시 회원가입 해주세요 ";
            url="/user/insertUserInfoProc";
        }
        model.addAttribute("title",title);
        model.addAttribute("state",state);
        model.addAttribute("msg",msg);
        model.addAttribute("url",url);



        log.info("insertUserInfo (회원가입 로직) 끝! ");

        return sweetalert;
    }


    /**
     *로그인 페이지
     * */
    @GetMapping("userLogin")
    public String userLogin(){
        log.info(this.getClass().getName()+"userLogin(page_Service 로그인 페이지)시작 ");
        return "/user/EGO_LOGIN_001";
    }
//
//    @PostMapping("userLoginProc")
//    public String userLoginProc(UserInfoDTO userInfoDTO, Model model )throws Exception{
//        log.info(this.getClass().getName()+"userLoginProc(page_Service 로그인 로직) 시작");
//
//        log.info("아이디랑 비밀번호 잘 가져왔니? : " + userInfoDTO.getUserPwd(), userInfoDTO.getUserId());
//
//        int res= userInfoService.getUserLogin(userInfoDTO.getUserId(), userInfoDTO.getUserPwd());
//
//        if (res == 1) {
//            title="로그인";
//            state="success";
//            msg="로그인 성공!";
//            url="/user/main";   //controller 주소ㅓ 작성
//
//        }else{
//            title="로그인 실패";
//            state="false";
//            msg="로그인 실패! 다시 로그인 해주세요 ";
//            url="/user/userLogin";
//        }
//        model.addAttribute("title",title);
//        model.addAttribute("state",state);
//        model.addAttribute("msg",msg);
//        model.addAttribute("url",url);
//
//
//
//        log.info("userLoginProc (page_Service 로그인 로직) 끝! ");
//
//        return sweetalert;
//
//    }

    /**
     * 아이디 찾기
     * */

    @GetMapping("findUserId")
    public String findUserId(){
        log.info(this.getClass().getName()+"findUserId(page_Service 아이디 찾기 페이지)시작 ");
        return "/user/findId";
    }

    @PostMapping("findUserIdProc")
    public String findUserIdProc(UserInfoDTO userInfoDTO, Model model) throws Exception {
        log.info(this.getClass().getName()+"findUserIdProc(page_Service 아이디 찾기 로직)시작 ");

        String userId=userInfoService.findUserId(userInfoDTO);

        if(userInfoDTO!=null){
            title = "아이디 찾기";
            state = "success";
            msg = "아이디는 : " + userId + " 입니다!";
            url = "/user/userLogin";
        }else {
            title = "아이디 찾기";
            state = "false";
            msg = "이름과 이메일을 다시한번 확인해 주세요";
            url = "/user/findUserId";

        }

        model.addAttribute("title",title);
        model.addAttribute("state",state);
        model.addAttribute("msg",msg);
        model.addAttribute("url",url);

        return sweetalert;
    }



}
