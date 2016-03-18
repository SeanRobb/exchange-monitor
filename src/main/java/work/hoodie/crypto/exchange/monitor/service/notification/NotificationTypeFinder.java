package work.hoodie.crypto.exchange.monitor.service.notification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.NotificationType;

@Component
public class NotificationTypeFinder {

    @Value("${slack.url:}")
    private String slackUrl;

    @Value("${email.address:}")
    private String emailAddress;

    @Value("${mail.host:}")
    private String mailHost;
    @Value("${mail.username:}")
    private String mailUsername;
    @Value("${mail.password:}")
    private String mailPassword;

    public NotificationType find() {
        if (isValidSlack()) {
            return NotificationType.SLACK;
        } else if(isValidEmail()) {
            return NotificationType.EMAIL;
        } else {
            throw new RuntimeException("Is not supported notification type");
        }

    }

    private Boolean isValidEmail() {
        return StringUtils.isBlank(slackUrl) && StringUtils.isNotBlank(emailAddress) &&
                StringUtils.isNotBlank(mailHost) && StringUtils.isNotBlank(mailUsername) &&
                StringUtils.isNotBlank(mailPassword) && StringUtils.isNotBlank(emailAddress);
    }

    private Boolean isValidSlack() {
        return StringUtils.isNotBlank(slackUrl) && StringUtils.isBlank(emailAddress) &&
                StringUtils.isBlank(mailHost) && StringUtils.isBlank(mailUsername) &&
                StringUtils.isBlank(mailPassword) && StringUtils.isBlank(emailAddress);
    }
}
