package com.nodeservice.sender;

import com.nodeservice.Configuration.Properties;
import org.springframework.core.env.Environment;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    private String username;
    private String password;
    private Environment myProperties;
    private java.util.Properties props;
    public MailSender(String username, String password) {
        this.myProperties = Properties.env;
        this.username = username;
        this.password = password;
        props = new java.util.Properties();
        props.put("mail.smtp.host", myProperties.getProperty("MAIL_SMTP_HOST"));
        props.put("mail.smtp.socketFactory.port", myProperties.getProperty("MAIL_SMTL_SOCKETFACTORY_PORT"));
        props.put("mail.smtp.socketFactory.class", myProperties.getProperty("MAIL_SMTL_SOCKETFACTORY_CLASS"));
        props.put("mail.smtp.auth", myProperties.getProperty("MAIL_SMTP_AUTH"));
        props.put("mail.smtp.port", myProperties.getProperty("MAIL_SMTP_PORT"));
    }

    /**
     *
     * @param subject
     * @param text
     * @param fromEmail
     * @param toEmail
     */
    public void send(String subject, String text, String fromEmail, String toEmail){
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            //от кого
            message.setFrom(new InternetAddress(username));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //тема сообщения
            message.setSubject(subject);
            //текст
            //message.setText(text);
            message.setContent(text, "text/html; charset=utf-8");
            //отправляем сообщение
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}