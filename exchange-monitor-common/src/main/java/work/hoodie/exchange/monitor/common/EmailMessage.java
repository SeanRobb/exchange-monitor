package work.hoodie.exchange.monitor.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    private String fromEmailAddress;
    private String toEmailAddress;
    private String subject;
    private String content;
}
