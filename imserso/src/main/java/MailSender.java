import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;

/**
 * Clase que permite enviar emails con Gmail.
 */
public class MailSender {

    String username;
    

    public MailSender(String username ) {
        this.username=username;
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        Properties props=new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.user", username);
        

        try {
            props.load(MailSender.class.getResourceAsStream("mailsender.properties"));
        } catch(IOException e) {
            throw new IllegalStateException(e);
        }

        Session session= Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));

            for(String destinatario: StringUtils.split(to, ";")) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));    
            }
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            Transport transport=session.getTransport("smtp");
            transport.connect("smtp.gmail.com", username , props.getProperty("mail.smtp.password"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        
    }

}
