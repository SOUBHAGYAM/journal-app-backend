package com.Ilearn.journalApp.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
public class EmailService {

	@Autowired(required = false)
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) {
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(text);
            javaMailSender.send(mail);
        }catch(Exception e){
            log.error("Email Service Error",e);
        }

    }
}
