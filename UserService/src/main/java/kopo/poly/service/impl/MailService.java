package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Slf4j
public class MailService implements IMailService {

    final String host ="smtp.gmail.com";

    final String user ="hjk8893@gmail.com";

    final String password ="wagcfdtidbayjssm";

    @Override
    public int doSendmail(MailDTO mDTO) throws Exception {
        int res = 1;
        log.info("메일발송 서비스 시작");
        if (mDTO == null) {
            mDTO = new MailDTO();
        }
        String tomail = CmmUtil.nvl(mDTO.getTomail());
        log.info("tomail : " + tomail);
        Properties props = new Properties();

        log.info("put start!!");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.transport.protocol", "smtps");
        log.info("put end !!");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        log.info("session end !!");

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(tomail));

            message.setSubject(CmmUtil.nvl(mDTO.getTitle()));

            message.setText(CmmUtil.nvl(mDTO.getContents()));

            Transport.send(message);

        } catch (MessagingException e) {
            res = 0;
            log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
        } catch (Exception e) {
            res = 0;
            log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
        }

        log.info("메일발송 서비스 종료");
        return res;
    }
}
