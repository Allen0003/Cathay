import org.example.coindesk.CoindeskDemoApplication;
import org.example.coindesk.dto.CurrencyInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CoindeskDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCurrencyCrud() {
        // Create
        CurrencyInfo currency = new CurrencyInfo();
        currency.setCode("USD");
        currency.setName("美元");
        currency.setRate(30.5);

        ResponseEntity<CurrencyInfo> createResp = restTemplate.postForEntity(
                "/api/currency", currency, CurrencyInfo.class);
        assertEquals(HttpStatus.CREATED, createResp.getStatusCode());

        // Read All
        ResponseEntity<CurrencyInfo[]> listResp = restTemplate.getForEntity(
                "/api/currency", CurrencyInfo[].class);
        assertEquals(HttpStatus.OK, listResp.getStatusCode());
        List<CurrencyInfo> list = Arrays.asList(listResp.getBody());
        assertTrue(list.stream().anyMatch(c -> "USD".equals(c.getCode())));

        // Update
        currency.setName("美金");
        currency.setRate(31.0);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CurrencyInfo> updateReq = new HttpEntity<>(currency, headers);

        ResponseEntity<CurrencyInfo> updateResp = restTemplate.exchange(
                "/api/currency/USD", HttpMethod.PUT, updateReq, CurrencyInfo.class);
        assertEquals(HttpStatus.OK, updateResp.getStatusCode());
        assertEquals("美金", updateResp.getBody().getName());

        // Read Single
        ResponseEntity<CurrencyInfo> singleResp = restTemplate.getForEntity(
                "/api/currency/USD", CurrencyInfo.class);
        assertEquals(HttpStatus.OK, singleResp.getStatusCode());
        assertEquals("美金", singleResp.getBody().getName());

        // Delete
        ResponseEntity<Void> deleteResp = restTemplate.exchange(
                "/api/currency/USD", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResp.getStatusCode());

        // Confirm Deletion
        ResponseEntity<CurrencyInfo> deletedResp = restTemplate.getForEntity(
                "/api/currency/USD", CurrencyInfo.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedResp.getStatusCode());
    }

}
