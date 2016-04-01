package work.hoodie.crypto.exchange.monitor.service.wallet;

import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.Wallet;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;

import java.math.BigDecimal;

@Component
public class WalletBalanceMarshaller {

    public WalletBalance convert(Wallet wallet, OpenOrder openOrder, Ticker ticker) {
        BigDecimal balance = wallet.getBalance();
        String currency = wallet.getCurrency();
        BigDecimal openOrderAmount = openOrder.getAmount();
        BigDecimal total = balance.add(openOrderAmount);
        BigDecimal btcValue = total.multiply(ticker.getLast());
        return new WalletBalance()
                .setOnOrder(openOrderAmount)
                .setAvailable(balance)
                .setCurrency(currency)
                .setBtcValue(btcValue);
    }

}
