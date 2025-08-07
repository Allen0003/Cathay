
import org.example.coindesk.dto.CoindeskResponse;
import org.example.coindesk.dto.CurrencyInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyMapperTest {
    @Test
    void testFlattenCurrencyList() {
        // Arrange: 準備測試資料
        CurrencyInfo usd = new CurrencyInfo();
        usd.setCode("USD");
        usd.setName("美元");
        usd.setRate(1.0);

        CurrencyInfo jpy = new CurrencyInfo();
        jpy.setCode("JPY");
        jpy.setName("日圓");
        jpy.setRate(130.25);

        CoindeskResponse response = new CoindeskResponse();
        response.setUpdatedTime("2025-08-06 12:00");
        response.setCurrencies(Arrays.asList(usd, jpy));

        // Act: 呼叫要測試的資料轉換方法
        List<String> result = flattenCurrencyList(response);

        // Assert: 驗證結果是否正確
        assertEquals(2, result.size());
        assertTrue(result.contains("USD:美元=1.0"));
        assertTrue(result.contains("JPY:日圓=130.25"));
    }

    // 你要測試的資料轉換方法
    public List<String> flattenCurrencyList(CoindeskResponse response) {
        List<String> result = new ArrayList<>();
        if (response.getCurrencies() != null) {
            for (CurrencyInfo currency : response.getCurrencies()) {
                result.add(currency.getCode() + ":" + currency.getName() + "=" + currency.getRate());
            }
        }
        return result;
    }
}
