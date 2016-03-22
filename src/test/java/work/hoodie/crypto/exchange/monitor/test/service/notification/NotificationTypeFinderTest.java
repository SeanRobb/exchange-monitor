package work.hoodie.crypto.exchange.monitor.test.service.notification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.hoodie.crypto.exchange.monitor.domain.NotificationType;
import work.hoodie.crypto.exchange.monitor.service.notification.NotificationTypeFinder;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class NotificationTypeFinderTest {

    @InjectMocks
    private NotificationTypeFinder notificationTypeFinder;

    @Test
    public void find_findsSlack_AllBlank() {
        NotificationType expected = NotificationType.SLACK;
        String slackUrl = "this is my slack url";
        String emailAddress = "";
        String emailHost = "";
        String emailUsername = "";
        String emailPassword = "";

        setVars(slackUrl, emailAddress, emailHost, emailUsername, emailPassword);

        NotificationType actual = notificationTypeFinder.find();

        assertEquals(expected,actual);

    }

    @Test
    public void find_findsEmail_AllBlank() {
        NotificationType expected = NotificationType.EMAIL;
        String slackUrl = "";
        String emailAddress = "address";
        String emailHost = "host";
        String emailUsername = "username";
        String emailPassword = "password";

        setVars(slackUrl, emailAddress, emailHost, emailUsername, emailPassword);

        NotificationType actual = notificationTypeFinder.find();

        assertEquals(expected,actual);
    }

    @Test(expected = Exception.class)
    public void find_throwsException_AllFilledOut() {
        String slackUrl = "test";
        String emailAddress = "address";
        String emailHost = "host";
        String emailUsername = "username";
        String emailPassword = "password";

        setVars(slackUrl, emailAddress, emailHost, emailUsername, emailPassword);

        NotificationType actual = notificationTypeFinder.find();

    }
    @Test(expected = Exception.class)
    public void find_throwsException_blankEmail() {
        String slackUrl = "test";
        String emailAddress = "";
        String emailHost = "host";
        String emailUsername = "username";
        String emailPassword = "password";

        setVars(slackUrl, emailAddress, emailHost, emailUsername, emailPassword);

        NotificationType actual = notificationTypeFinder.find();

    }
    @Test(expected = Exception.class)
    public void find_throwsException_blankHost() {
        String slackUrl = "test";
        String emailAddress = "address";
        String emailHost = "";
        String emailUsername = "username";
        String emailPassword = "password";

        setVars(slackUrl, emailAddress, emailHost, emailUsername, emailPassword);

        NotificationType actual = notificationTypeFinder.find();

    }
    @Test(expected = Exception.class)
    public void find_throwsException_blankUsername() {
        String slackUrl = "test";
        String emailAddress = "address";
        String emailHost = "host";
        String emailUsername = "";
        String emailPassword = "password";

        setVars(slackUrl, emailAddress, emailHost, emailUsername, emailPassword);

        NotificationType actual = notificationTypeFinder.find();

    }
    @Test(expected = Exception.class)
    public void find_throwsException_blankPassword() {
        String slackUrl = "test";
        String emailAddress = "address";
        String emailHost = "host";
        String emailUsername = "username";
        String emailPassword = "";

        setVars(slackUrl, emailAddress, emailHost, emailUsername, emailPassword);

        NotificationType actual = notificationTypeFinder.find();

    }

    private void setVars(String slackUrl, String emailAddress, String emailHost, String emailUsername, String emailPassword) {
        ReflectionTestUtils.setField(notificationTypeFinder, "slackUrl", slackUrl);
        ReflectionTestUtils.setField(notificationTypeFinder, "emailAddress", emailAddress);
        ReflectionTestUtils.setField(notificationTypeFinder, "mailHost", emailHost);
        ReflectionTestUtils.setField(notificationTypeFinder, "mailUsername", emailUsername);
        ReflectionTestUtils.setField(notificationTypeFinder, "mailPassword", emailPassword);
    }

}
