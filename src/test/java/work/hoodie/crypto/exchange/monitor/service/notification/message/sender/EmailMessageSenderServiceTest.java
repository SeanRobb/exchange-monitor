package work.hoodie.crypto.exchange.monitor.service.notification.message.sender;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import work.hoodie.crypto.exchange.monitor.domain.EmailMessage;
import work.hoodie.crypto.exchange.monitor.service.notification.message.sender.EmailMessageSenderService;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailMessageSenderServiceTest {

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MimeMessage mimeMessage;


    @Test
    public void testSend() throws Exception {
        String fromEmail = "FromEmail";
        String toEmail = "TOEmail";
        String subject = "subject";
        String content = "Content";
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setToEmailAddress(toEmail);
        emailMessage.setFromEmailAddress(fromEmail);
        emailMessage.setSubject(subject);
        emailMessage.setContent(content);

        EmailMessageSenderService classUnderTest = new EmailMessageSenderService(javaMailSender);

        when(javaMailSender.createMimeMessage())
                .thenReturn(mimeMessage);

        classUnderTest.send(emailMessage);

        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(eq(mimeMessage));
        verify(mimeMessage).setFrom(new InternetAddress(fromEmail));
        verify(mimeMessage).addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        verify(mimeMessage).setSubject(subject);
        verify(mimeMessage).setContent(content, EmailMessageSenderService.TEXT_HTML);
    }
}
