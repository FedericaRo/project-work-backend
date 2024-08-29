package com.generation.progetto_finale.services;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service //spirng sa che deve iniettare questo servizio
public class MailService 
{

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendHtmlMessage(String to, String subject, Map<String, Object> templateModel) throws MessagingException 
    {
        Context context = new Context();
        context.setVariables(templateModel);
        String htmlBody = templateEngine.process("emailTemplate", context);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        emailSender.send(message);
    }

    public void sendHtmlMessageWithAttachment(String to, String subject, Map<String, Object> templateModel, String pathToAttachment) throws MessagingException 
    {
        Context context = new Context();
        context.setVariables(templateModel);
        String htmlBody = templateEngine.process("emailTemplate", context);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        
        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment(file.getFilename(), file);

        emailSender.send(message);
    }
}
