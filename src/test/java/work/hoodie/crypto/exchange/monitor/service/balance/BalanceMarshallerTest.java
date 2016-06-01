package work.hoodie.crypto.exchange.monitor.service.balance;


import com.xeiam.xchange.dto.trade.Wallet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.Balance;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BalanceMarshallerTest {
    @InjectMocks
    private BalanceMarshaller balanceMarshaller;

    @Test
    public void getWallet() throws Exception {
        Balance balance = new Balance()
                .setCurrency("SEAN")
                .setOnOrder(BigDecimal.ONE)
                .setAvailable(BigDecimal.valueOf(9));

        Wallet convert = balanceMarshaller.getWallet(balance);

        assertEquals(balance.getCurrency(), convert.getCurrency());
        assertEquals(balance.getAvailable(), convert.getBalance());
    }

    @Test
    public void getBalance() throws Exception {
        String currency = "SEAN";
        BigDecimal total = BigDecimal.valueOf(11);
        Wallet wallet = new Wallet(currency, BigDecimal.TEN);
        OpenOrder openOrder = new OpenOrder()
                .setCurrency(currency)
                .setAmount(BigDecimal.ONE);

        Balance convert = balanceMarshaller.getBalance(wallet, openOrder);

        assertEquals(wallet.getCurrency(), convert.getCurrency());
        assertEquals(wallet.getBalance(), convert.getAvailable());
        assertEquals(openOrder.getAmount(), convert.getOnOrder());
        assertEquals(total, convert.getTotal());
    }

    @Test
    public void getOpenOrder() throws Exception {
        String currency = "SEAN";
        BigDecimal onOrder = BigDecimal.ONE;

        Balance balance = new Balance()
                .setCurrency(currency)
                .setOnOrder(onOrder);

        OpenOrder openOrder = balanceMarshaller.getOpenOrder(balance);

        assertEquals(onOrder, openOrder.getAmount());
        assertEquals(currency, openOrder.getCurrency());
    }

}
