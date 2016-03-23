package work.hoodie.crypto.exchange.monitor.service.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationSettingsStringifier {
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

    public String getSettings(){
        return null;
    }
}
