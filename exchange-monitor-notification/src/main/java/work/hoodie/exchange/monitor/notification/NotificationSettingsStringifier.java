package work.hoodie.exchange.monitor.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.NotificationType;

@Component
public class NotificationSettingsStringifier {
    @Autowired
    private NotificationTypeFinder notificationTypeFinder;
    @Value("${slack.url:}")
    private String slackUrl;
    @Value("${email.address:}")
    private String emailAddress;
    @Value("${email.server.host:}")
    private String mailHost;
    @Value("${email.server.port:25}")
    private int mailPort;
    @Value("${email.server.username:}")
    private String mailUsername;
    @Value("${email.server.password:}")
    private String mailPassword;

    public String getSettings() {
        Enum notificationType = notificationTypeFinder.find();

        if (notificationType == NotificationType.EMAIL) {
            String email = "Notification Type: " + NotificationType.EMAIL + "\n" +
                    "Email Address: " + emailAddress +
                    "SMTP Mail Host: " + mailHost +
                    "SMTP Mail Port: " + mailPort +
                    "SMTP Mail Username: " + mailUsername +
                    "SMTP Mail Password: " + mailPassword;
            return email;
        } else if (notificationType == NotificationType.SLACK) {
            return "Notification Type: " + NotificationType.SLACK + " " + slackUrl;
        } else {
            return "Notification Type: " + NotificationType.NONE;
        }
    }
}





