package work.hoodie.crypto.exchange.monitor.test.service.wallet;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;
import work.hoodie.crypto.exchange.monitor.domain.WalletComparison;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletComparisonService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class WalletComparisonServiceTest {
    @InjectMocks
    private WalletComparisonService classUnderTest;

    @Test
    public void testBuild() throws Exception {
        WalletBalance oldBalance = new WalletBalance()
                .setCurrency("SomeCoinage")
                .setAvailable(BigDecimal.valueOf(2))
                .setOnOrder(BigDecimal.valueOf(7))
                .setBtcValue(BigDecimal.valueOf(4));
        WalletBalance newBalance = new WalletBalance()
                .setCurrency("SomeCoinage")
                .setAvailable(BigDecimal.valueOf(3))
                .setOnOrder(BigDecimal.valueOf(4))
                .setBtcValue(BigDecimal.valueOf(9));

        WalletComparison expected = new WalletComparison();
        expected.setCurrency("SomeCoinage");
        expected.setBalanceGain(BigDecimal.valueOf(-2));
        expected.setBtcValueGain(BigDecimal.valueOf(5));
        expected.setNewBalance(newBalance);
        expected.setOldBalance(oldBalance);

        WalletComparison actual = classUnderTest.compare(oldBalance, newBalance);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindMatchingWalletCurrency() throws Exception {
        String currency1 = "DOGE";
        String currency2 = "DGB";
        List<WalletBalance> walletBalanceList = new ArrayList<WalletBalance>();
        WalletBalance expected = new WalletBalance()
                .setCurrency(currency1)
                .setAvailable(BigDecimal.valueOf(2))
                .setBtcValue(BigDecimal.valueOf(4));
        walletBalanceList.add(expected);
        walletBalanceList.add(new WalletBalance()
                .setCurrency(currency2)
                .setAvailable(BigDecimal.valueOf(4))
                .setBtcValue(BigDecimal.valueOf(1)));
        WalletBalance walletBalance = new WalletBalance()
                .setCurrency(currency1)
                .setAvailable(BigDecimal.valueOf(2))
                .setBtcValue(BigDecimal.valueOf(6));

        WalletBalance actual = classUnderTest.findMatchingWalletCurrency(walletBalanceList, walletBalance);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindMatchingWalletCurrency_NoMatch() throws Exception {
        String currency1 = "DOGE";
        String currency2 = "DGB";
        String currency3 = "SpecialCoin";
        List<WalletBalance> walletBalanceList = new ArrayList<WalletBalance>();
        WalletBalance notExpected1 = new WalletBalance()
                .setCurrency(currency1)
                .setAvailable(BigDecimal.valueOf(2))
                .setBtcValue(BigDecimal.valueOf(4));
        WalletBalance notExpected2 = new WalletBalance()
                .setCurrency(currency2)
                .setAvailable(BigDecimal.valueOf(4))
                .setBtcValue(BigDecimal.valueOf(1));
        walletBalanceList.add(notExpected1);
        walletBalanceList.add(notExpected2);
        WalletBalance walletBalance = new WalletBalance()
                .setCurrency(currency3)
                .setAvailable(BigDecimal.valueOf(2))
                .setBtcValue(BigDecimal.valueOf(6));

        WalletBalance actual = classUnderTest.findMatchingWalletCurrency(walletBalanceList, walletBalance);

        assertNotNull(actual);
        assertNotEquals(notExpected1, actual);
        assertNotEquals(notExpected2, actual);
        assertEquals(BigDecimal.ZERO, actual.getAvailable());
        assertEquals(BigDecimal.ZERO, actual.getBtcValue());
        assertEquals(currency3, actual.getCurrency());
    }
}
