package kopo.poly.controller.rest;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class MailController {

    private final IMailService mailService;
    private int res=  0;


    @PostMapping(value = "sendmail") // 메일 발송하기
    public Map<String, String> sendmail(HttpServletRequest request, UserDTO uDTO) throws Exception {
        log.info(this.getClass().getName() + "sendmail start!!");

        String email = CmmUtil.nvl(request.getParameter("userEmail"));
        // 이메일 주소 확인
        log.info("email :" + email);

        Random random = new Random();
        int randomPin = random.nextInt(888888) + 111111; //인증번호

        String title = "반갑습니다. 환영합니다.";
        String contents = "(인증번호 :" + randomPin + ")";
        log.info("randomPIN: " + randomPin);

        MailDTO mDTO = new MailDTO();
        mDTO.setTomail(email);
        mDTO.setTitle(title);
        mDTO.setContents(contents);
        mDTO.setRandompin(randomPin);

        res = mailService.doSendmail(mDTO);

        if (res == 1) {
            log.info(this.getClass().getName() + "메일 발송 성공!");
        } else {
            log.info(this.getClass().getName() + "메일 발송 실패!");
        }

        Map<String, String> pMap = new HashMap<>();
        pMap.put("res", String.valueOf(res));
        pMap.put("ramdomPin", String.valueOf(randomPin));

        log.info(this.getClass().getName() + "sendmail end!!");
        return pMap;
    }

}
