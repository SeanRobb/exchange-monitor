package work.hoodie.crypto.exchange.monitor.service.wallet;


import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import work.hoodie.crypto.exchange.monitor.domain.OpenOrder;
import work.hoodie.crypto.exchange.monitor.domain.WalletBalance;
import work.hoodie.crypto.exchange.monitor.service.trade.open.OpenOrderRetriever;
import work.hoodie.crypto.exchange.monitor.service.trade.open.OpenOrderService;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletBalanceFactory;
import work.hoodie.crypto.exchange.monitor.service.wallet.WalletRetrieverService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WalletRetrieverServiceTest {
    @InjectMocks
    private WalletRetrieverService walletRetrieverService;
    @Mock
    private PollingAccountService pollingAccountService;
    @Mock
    private WalletBalanceFactory walletBalanceFactory;
    @Mock
    private WalletBalance walletBalance;
    @Mock
    private OpenOrderService openOrderService;
    @Mock
    private OpenOrderRetriever openOrderRetriever;

    @Test
    public void testGetWallets() throws Exception {

        List<OpenOrder> openOrders = new ArrayList<OpenOrder>();
        HashMap<String, Wallet> walletHashMap = new HashMap<String, Wallet>();
        walletHashMap.put("Something", new Wallet("Something", BigDecimal.ONE));
        walletHashMap.put("Else", new Wallet("Else", BigDecimal.ONE));

        when(pollingAccountService.getAccountInfo())
                .thenReturn(new AccountInfo("userName", walletHashMap));
        when(walletBalanceFactory.getBalance(any(Wallet.class), any(OpenOrder.class)))
                .thenReturn(walletBalance);
        when(openOrderRetriever.getOpenOrders())
                .thenReturn(openOrders);
        when(openOrderService.findTotalOpenOrdersForCurrency(anyList(), anyString()))
                .thenCallRealMethod();

        List<WalletBalance> actual = walletRetrieverService.getWalletBalances();

        assertNotNull(actual);
        verify(pollingAccountService).getAccountInfo();
        for (Wallet wallet : walletHashMap.values()) {
            verify(walletBalanceFactory).getBalance(eq(wallet), any(OpenOrder.class));
        }
        assertFalse(actual.isEmpty());
        for (WalletBalance balance : actual) {
            assertEquals(walletBalance, balance);
        }

    }
}
