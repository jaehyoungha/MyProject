package kopo.poly.controller.rest;

import kopo.poly.service.P_IMailService;
import kopo.poly.service.P_IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class P_RestUserController {


    private final P_IUserService userInfoService;
    private final P_IMailService mailService;


    /**
     * 아이디 중복검사
     */
    @PostMapping("checkUserId")
    public int checkUserId(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + "checkUserId(page_service 아이디 중복검사 시작) start");

        log.info("아이디 중복 체크 : " + request.getParameter("userId"));


        int res = userInfoService.checkUserId(request.getParameter("userId"));


        return res;
    }


    @PostMapping(value = "checkUserEmail")
    public ResponseEntity<Integer> checkUserEmail(@RequestBody String userEmail) throws Exception {
        log.info(this.getClass().getName() + "checkUserEmail(user_Service 이메일 중복체크 시작) START");


        int res = userInfoService.checkUserEmail(userEmail);

        return ResponseEntity.ok().body(res);
    }


    /**
     * 이메일 발송
     */
    @PostMapping("doSendmail")
    public Map<String, String> doSendmail(HttpServletRequest request) {
        log.info(this.getClass().getName() + "doSendmail (pager_Service 이메일 전달 시작) start");

        log.info("이메일 페이지컨트롤러 보내기 체크 : " + request.getParameter("userEmail"));
        Map res = mailService.doSendmail(request.getParameter("userEmail"));


        return res;
    }
}
