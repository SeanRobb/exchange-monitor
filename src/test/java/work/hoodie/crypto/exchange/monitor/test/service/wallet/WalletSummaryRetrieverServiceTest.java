package work.hoodie.crypto.exchange.monitor.test.service.wallet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;
import work.hoodie.crypto.exchange.monitor.domain.WalletSummary;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletComparisonService;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletRetrieverService;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletSummaryRetrieverService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WalletSummaryRetrieverServiceTest {

    @InjectMocks
    private WalletSummaryRetrieverService classUnderTest;

    @Mock
    private WalletComparisonService walletComparisonService;
    @Mock
    private WalletRetrieverService walletRetrieverService;
    @Mock
    private List<WalletBalance> walletBalanceMock1;
    @Mock
    private List<WalletBalance> walletBalanceMock2;
    @Mock
    private List<WalletBalance> walletBalanceMock3;


    @Test
    public void testGetNewWallets() throws Exception {
        classUnderTest.retrieveNewWallets();

        verify(walletRetrieverService).getWalletBalances();
    }

    @Test
    public void testSync() throws Exception {
        ReflectionTestUtils.setField(classUnderTest, "oldWalletBalances", walletBalanceMock1);
        ReflectionTestUtils.setField(classUnderTest, "newWalletBalances", walletBalanceMock2);

        when(walletRetrieverService.getWalletBalances())
                .thenReturn(walletBalanceMock3);

        classUnderTest.sync();


        List<WalletBalance> oldWalletBalances = (List<WalletBalance>) ReflectionTestUtils
                .getField(classUnderTest, "oldWalletBalances");
        List<WalletBalance> newWalletBalances = (List<WalletBalance>) ReflectionTestUtils
                .getField(classUnderTest, "newWalletBalances");

        assertEquals(oldWalletBalances, walletBalanceMock2);
        verify(walletRetrieverService).getWalletBalances();
        assertEquals(newWalletBalances, walletBalanceMock3);
    }

    @Test
    public void testGetWalletComparisons() throws Exception {
        String currency1 = "DOGE";
        List<WalletBalance> walletBalance1 = new ArrayList<WalletBalance>();
        walletBalance1.add(new WalletBalance()
                .setCurrency(currency1)
                .setAvailable(BigDecimal.valueOf(2))
                .setOnOrder(BigDecimal.ZERO)
                .setBtcValue(BigDecimal.valueOf(4)));
        List<WalletBalance> walletBalance2 = new ArrayList<WalletBalance>();
        walletBalance2.add(new WalletBalance()
                .setCurrency(currency1)
                .setAvailable(BigDecimal.valueOf(2))
                .setOnOrder(BigDecimal.ZERO)
                .setBtcValue(BigDecimal.valueOf(6)));
        ReflectionTestUtils.setField(classUnderTest, "newWalletBalances", walletBalance1);

        when(walletComparisonService.compare(any(WalletBalance.class), any(WalletBalance.class)))
                .thenCallRealMethod();
        when(walletComparisonService.findMatchingWalletCurrency(anyList(), any(WalletBalance.class)))
                .thenCallRealMethod();

        when(walletRetrieverService.getWalletBalances())
                .thenReturn(walletBalance2);

        WalletSummary actual = classUnderTest.getWalletSummary();


        assertNotNull(actual);
        verify(walletComparisonService).compare(any(WalletBalance.class), any(WalletBalance.class));
        assertFalse(actual.getWalletComparisons().isEmpty());
        assertEquals(currency1, actual.getWalletComparisons().get(0).getCurrency());
    }

    @Test
    public void testGetWalletComparisons_MultipleWallets() throws Exception {
        String currency1 = "DOGE";
        String currency2 = "DGB";
        List<WalletBalance> walletBalance1 = new ArrayList<WalletBalance>();
        walletBalance1.add(new WalletBalance()
                .setCurrency(currency1)
                .setAvailable(BigDecimal.valueOf(2))
                .setOnOrder(BigDecimal.ZERO)
                .setBtcValue(BigDecimal.valueOf(4)));
        walletBalance1.add(new WalletBalance()
                .setCurrency(currency2)
                .setAvailable(BigDecimal.valueOf(4))
                .setOnOrder(BigDecimal.ZERO)
                .setBtcValue(BigDecimal.valueOf(1)));
        List<WalletBalance> walletBalance2 = new ArrayList<WalletBalance>();
        walletBalance2.add(new WalletBalance()
                .setCurrency(currency1)
                .setAvailable(BigDecimal.valueOf(2))
                .setOnOrder(BigDecimal.ZERO)
                .setBtcValue(BigDecimal.valueOf(6)));
        walletBalance2.add(new WalletBalance()
                .setCurrency(currency2)
                .setAvailable(BigDecimal.valueOf(4))
                .setOnOrder(BigDecimal.ZERO)
                .setBtcValue(BigDecimal.valueOf(2)));

        ReflectionTestUtils.setField(classUnderTest, "newWalletBalances", walletBalance1);

        when(walletRetrieverService.getWalletBalances())
                .thenReturn(walletBalance2);
        when(walletComparisonService.compare(any(WalletBalance.class), any(WalletBalance.class)))
                .thenCallRealMethod();
        when(walletComparisonService.findMatchingWalletCurrency(anyList(), any(WalletBalance.class)))
                .thenCallRealMethod();

        WalletSummary actual = classUnderTest.getWalletSummary();


        assertNotNull(actual);
        verify(walletComparisonService, atLeast(1)).compare(any(WalletBalance.class), any(WalletBalance.class));
        assertFalse(actual.getWalletComparisons().isEmpty());
        assertEquals(2, actual.getWalletComparisons().size());
        assertEquals(currency1, actual.getWalletComparisons().get(0).getCurrency());
        assertEquals(currency2, actual.getWalletComparisons().get(1).getCurrency());
    }

}
