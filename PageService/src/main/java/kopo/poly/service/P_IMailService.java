package kopo.poly.service;

import java.util.Map;

public interface P_IMailService {

    //이메일 인증번호 발송
    Map<String, String> doSendmail(String userEmail);
}
