package com.astarus.documentmanagementsystem.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // set your mail server host, port, username, password, etc.
        // можно попробовать https://github.com/maildev/maildev в докере
        return mailSender;
    }
}
