package com.fis.crm.config;

import com.fis.crm.security.EncodeAndDecode;
import com.fis.crm.domain.EmailConfig;
import com.fis.crm.repository.EmailConfigRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.Properties;

//@Configuration
@Component
public class EmailServerConfig {

//    @Bean
    private final EmailConfigRepository emailConfigRepository;

    public EmailServerConfig(EmailConfigRepository emailConfigRepository) {
        this.emailConfigRepository = emailConfigRepository;
    }

    public JavaMailSender getJavaMailSender() throws NumberFormatException, Exception{
        List<EmailConfig> emailList = emailConfigRepository.findAll();
        if(emailList.size()<1){
            throw new NullPointerException("The email server is not configured yet.");
        }
        EmailConfig email = emailList.get(0);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(email.getHost());
        mailSender.setPort(Integer.parseInt(email.getPort()));//ssl: 465, tls: 587
        mailSender.setUsername(email.getEmail());

        //decrypt password
        String password = EncodeAndDecode.decrypt(email.getPassword());
        System.out.println("password :"+password);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        if(email.getSsl().equals("1")){
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtps.ssl.checkserveridentity", "true");
            props.put("mail.smtps.ssl.trust", "*");
        }else{
            props.put("mail.smtp.starttls.enable", "true");
        }
//        props.put("mail.debug", "true");
        return mailSender;
    }
}
