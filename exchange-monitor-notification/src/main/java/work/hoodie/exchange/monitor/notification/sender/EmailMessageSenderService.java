package work.hoodie.exchange.monitor.notification.sender;

import org.springframework.mail.javamail.JavaMailSender;
import work.hoodie.exchange.monitor.common.EmailMessage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailMessageSenderService {

    public static final String TEXT_HTML = "text/html";
    private JavaMailSender javaMailSender;

    public EmailMessageSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(EmailMessage message) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            InternetAddress fromAddress = new InternetAddress(message.getFromEmailAddress());
            InternetAddress toAddress = new InternetAddress(message.getToEmailAddress());
            mimeMessage.setFrom(fromAddress);
            mimeMessage.addRecipient(Message.RecipientType.TO, toAddress);
            mimeMessage.setSubject(message.getSubject());
            mimeMessage.setContent(message.getContent(), TEXT_HTML);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
