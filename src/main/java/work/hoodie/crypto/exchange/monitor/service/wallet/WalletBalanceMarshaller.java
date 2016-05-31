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
        BigDecimal availableAmount = wallet.getBalance();
        String currency = wallet.getCurrency();
        BigDecimal openOrderAmount = openOrder.getAmount();
        BigDecimal total = availableAmount.add(openOrderAmount);
        BigDecimal lastPrice = ticker.getLast();
        BigDecimal btcValue = total.multiply(lastPrice);
        return new WalletBalance()
                .setOnOrder(openOrderAmount)
                .setAvailable(availableAmount)
                .setLastPrice(lastPrice)
                .setCurrency(currency)
                .setBtcValue(btcValue);
    }

}
