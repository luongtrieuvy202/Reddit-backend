package com.programming.technie.springredditclone.service;

import com.programming.technie.springredditclone.exception.SpringRedditException;
import com.programming.technie.springredditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import javax.swing.*;


@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;


    @Async
    void sendMail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
            messageHelper.setText(notificationEmail.getBody());
        };
        try{
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        }catch(MailException e){
            throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
        }
    }
}
