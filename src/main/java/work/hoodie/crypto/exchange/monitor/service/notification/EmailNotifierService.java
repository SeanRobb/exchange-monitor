package work.hoodie.crypto.exchange.monitor.service.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailNotifierService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailNotifierService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(EmailMessage email) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        InternetAddress fromAddress = new InternetAddress(email.getFromEmailAddress());
        InternetAddress toAddress = new InternetAddress(email.getToEmailAddress());

        mimeMessage.setFrom(fromAddress);
        mimeMessage.addRecipient(Message.RecipientType.TO, toAddress);
        mimeMessage.setSubject(email.getSubject());
        mimeMessage.setContent(email.getContent(), "text/html");

        javaMailSender.send(mimeMessage);
    }
}
