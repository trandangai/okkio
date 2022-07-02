package io.okkio.services.impl;

import io.okkio.configs.AppProperties;
import io.okkio.services.EmailServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * EmailServicesImpl
 * @author aitd
 */
@Slf4j
@Service
public class EmailServicesImpl implements EmailServices {
    @Autowired
    private AppProperties appProperties;

    @Override
    public boolean sendMailForgetPassword(String email, String firstName, String newPassword) {
        String subject = "Reset Your OKKIO Account Password";
        String content = "Dear " + firstName
                + "\n\n      New your password is " + newPassword
                + "\n\n"
                + "Thank you!"
                + "\n"
                + "OKKIO Team";
        return sendMail(appProperties.getEmail(), email, subject, content);
    }

    private boolean sendMail(String sender, String receiver, String subject, String content) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(appProperties.getEmail(), appProperties.getPassword());
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiver)
            );
            message.setSubject(subject);
            message.setText(content);
//            message.setText("Dear Mail Crawler,"
//                    + "\n\n Please do not spam my email!");

            Transport.send(message);
            log.info("Sent mail - Transport*****");
        } catch (MessagingException e) {
            log.warn("Don't send mail: " + e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}