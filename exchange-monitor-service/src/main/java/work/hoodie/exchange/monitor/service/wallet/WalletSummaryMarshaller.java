package work.hoodie.exchange.monitor.service.wallet;

import com.xeiam.xchange.dto.marketdata.Ticker;
import org.springframework.stereotype.Component;
import work.hoodie.exchange.monitor.common.Balance;
import work.hoodie.exchange.monitor.common.WalletSummary;

import java.math.BigDecimal;

@Component
public class WalletSummaryMarshaller {


    public WalletSummary convert(Ticker ticker, Balance balance) {
        BigDecimal lastPrice = ticker.getLast();
        BigDecimal total = balance.getTotal();
        BigDecimal btcValue = total.multiply(lastPrice);
        return new WalletSummary()
                .setLastPrice(lastPrice)
                .setBtcValue(btcValue)
                .setBalance(balance);
    }

}
