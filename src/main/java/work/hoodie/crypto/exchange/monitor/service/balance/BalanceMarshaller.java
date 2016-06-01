package work.hoodie.crypto.exchange.monitor.service.balance;

import com.xeiam.xchange.dto.trade.Wallet;
import org.springframework.stereotype.Component;
import work.hoodie.crypto.exchange.monitor.domain.Balance;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;

@Component
public class BalanceMarshaller {

    public Balance getBalance(Wallet wallet, OpenOrder openOrder) {
        return new Balance()
                .setCurrency(wallet.getCurrency())
                .setAvailable(wallet.getBalance())
                .setOnOrder(openOrder.getAmount());
    }

    public Wallet getWallet(Balance balance) {
        return new Wallet(balance.getCurrency(), balance.getAvailable());
    }

    public OpenOrder getOpenOrder(Balance balance) {
        return new OpenOrder()
                .setCurrency(balance.getCurrency())
                .setAmount(balance.getOnOrder());
    }

}
