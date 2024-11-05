import javax.mail.MessagingException;

import org.junit.Test;

public class TestMailSender {


    @Test
    public void probarMensaje() throws MessagingException {
        MailSender sender=new MailSender("luismiravalles@gmail.com");

        sender.sendEmail("luismiravalles@gmail.com", "probando DE NUEVO", "Este es el contenido.");
    }

}
