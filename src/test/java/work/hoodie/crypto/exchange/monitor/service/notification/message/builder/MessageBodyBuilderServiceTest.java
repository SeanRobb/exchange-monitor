package work.hoodie.crypto.exchange.monitor.service.notification.message.builder;


import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.UserTrade;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparison;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;
import work.hoodie.crypto.exchange.monitor.service.notification.message.builder.MessageBodyBuilderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class MessageBodyBuilderServiceTest {

    @InjectMocks
    private MessageBodyBuilderService classUnderTest;

    @Test
    public void testBuild() throws Exception {
        UserTrade userTrade = new UserTrade.Builder()
                .type(Order.OrderType.BID)
                .tradableAmount(BigDecimal.TEN)
                .currencyPair(CurrencyPair.DOGE_LTC)

                .price(BigDecimal.ONE)
                .timestamp(new Date())
                .id("1")

                .feeAmount(BigDecimal.ZERO)
                .feeCurrency("LTC")
                .orderId("2")
                .build();

        String message = classUnderTest.build(userTrade);

        assertNotNull(message);
    }

    @Test
    public void testBuild1() throws Exception {
        String currency = "DGB";
        List<WalletComparison> walletComparisons = new ArrayList<WalletComparison>();
        WalletComparison walletComparison = new WalletComparison();
        WalletBalance newBalance = new WalletBalance()
                .setCurrency(currency)
                .setBtcValue(BigDecimal.ONE)
                .setAvailable(BigDecimal.ZERO)
                .setOnOrder(BigDecimal.ONE);
        WalletBalance oldBalance = new WalletBalance()
                .setCurrency(currency)
                .setBtcValue(BigDecimal.ZERO)
                .setAvailable(BigDecimal.ZERO)
                .setOnOrder(BigDecimal.ZERO);
        walletComparison.setCurrency(currency);
        walletComparison.setBtcValueGain(BigDecimal.ONE);
        walletComparison.setBalanceGain(BigDecimal.ONE);
        walletComparison.setNewBalance(newBalance);
        walletComparison.setOldBalance(oldBalance);
        walletComparisons.add(walletComparison);
        WalletSummary walletSummary = new WalletSummary();
        walletSummary.setBtcTotalChange(BigDecimal.ZERO);
        walletSummary.setWalletComparisons(walletComparisons);

        String message = classUnderTest.build(walletSummary);
        log.info("\n"+message);
        assertNotNull(message);
    }
}
