/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Nicolas PC
 */
public class Mail {
    
    private final static String KEY_SMTP_SERVER = "mail.smtp.host";
    private final static String KEY_FROM = "mail.smtp.user";
    private final static String KEY_PASSWORD = "mail.smtp.password";
    
    private static Properties props;
    
    private static void loadConfig() {
        if (props == null) {
            props = new Properties();
            props.put(KEY_SMTP_SERVER, "smtp.gmail.com");
            props.put(KEY_FROM, "digitaleventssystem@gmail.com");
            props.put(KEY_PASSWORD, "gaesevents");
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            props.put("mail.smtp.port", "587");
        }
    }
    
    public static void sendMail(String destinatario, String asunto, String cuerpo) {
        loadConfig();
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(props.getProperty(KEY_FROM)));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            
            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty(KEY_SMTP_SERVER), props.getProperty(KEY_FROM), props.getProperty(KEY_PASSWORD));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
        } catch (MessagingException me) {
            me.printStackTrace();
        }        
    }
    
    public static void sendMailHtml(String destinatarios, String asunto, String cuerpoHtml) {
        loadConfig();
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(props.getProperty(KEY_FROM)));
            message.addRecipients(Message.RecipientType.TO, destinatarios);
            message.setSubject(asunto);
            
            Multipart parts = new MimeMultipart();
            BodyPart bodyMail = new MimeBodyPart();
            bodyMail.setContent(cuerpoHtml, "text/html");
            parts.addBodyPart(bodyMail);
            message.setContent(parts);
            
            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty(KEY_SMTP_SERVER), props.getProperty(KEY_FROM), props.getProperty(KEY_PASSWORD));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
        } catch (MessagingException me) {
            me.printStackTrace();
        }        
    }
    
    public static void sendMailHtmlFile(String destinatarios, String asunto, String cuerpoHtml, List<File> files) throws IOException {
        loadConfig();
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(props.getProperty(KEY_FROM)));
            message.addRecipients(Message.RecipientType.TO, destinatarios);
            message.setSubject(asunto);
            
            Multipart parts = new MimeMultipart();
            BodyPart bodyMail = new MimeBodyPart();
            bodyMail.setContent(cuerpoHtml, "text/html");
            parts.addBodyPart(bodyMail);
            for (File file : files) {
                MimeBodyPart adjunto = new MimeBodyPart();
                adjunto.attachFile(file);
                parts.addBodyPart(adjunto);
            }
            message.setContent(parts);
            
            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty(KEY_SMTP_SERVER), props.getProperty(KEY_FROM), props.getProperty(KEY_PASSWORD));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
        } catch (MessagingException me) {
            me.printStackTrace();
        }        
    }
    
    public static void sendMailMasivo (String[] destinatarios, String asunto, String cuerpoHtml) {
        
    }
}
