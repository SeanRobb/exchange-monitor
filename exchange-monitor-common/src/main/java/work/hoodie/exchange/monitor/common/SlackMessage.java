package work.hoodie.exchange.monitor.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class SlackMessage {
    private String text;
    private String icon_emoji ;
    private String username ;
}
