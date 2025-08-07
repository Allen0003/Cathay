import org.example.coindesk.CoindeskDemoApplication;
import org.example.coindesk.dto.CoindeskResponse;
import org.example.coindesk.service.CoindeskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = CoindeskDemoApplication.class)
public class CoindeskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoindeskService coindeskService;

    @Test
    void testGetRawData() throws Exception {
        String mockRawData = "{\"time\":\"2025-08-06T00:00:00Z\",\"bpi\":{}}";

        when(coindeskService.getRawData()).thenReturn(mockRawData);

        mockMvc.perform(get("/api/coindesk/raw"))
                .andExpect(status().isOk())
                .andExpect(content().json(mockRawData))
                .andDo(result -> System.out.println("Raw API Response: " + result.getResponse().getContentAsString()));
    }

    @Test
    void testGetConvertedData_success() throws Exception {
        CoindeskResponse mockResponse = new CoindeskResponse();
        mockResponse.setUpdatedTime("2025-08-06");
        mockResponse.setCurrencies(Collections.emptyList());

        when(coindeskService.getConvertedData()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/coindesk/converted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updatedTime").value("2025-08-06"))
                .andExpect(jsonPath("$.currencies").isArray())
                .andDo(result -> System.out.println("Converted API Response: " + result.getResponse().getContentAsString()));
    }

    @Test
    void testGetConvertedData_error() throws Exception {
        when(coindeskService.getConvertedData()).thenThrow(new RuntimeException("轉換失敗"));

        mockMvc.perform(get("/api/coindesk/converted"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("轉換失敗"))
                .andDo(result -> System.out.println("Error Response: " + result.getResponse().getContentAsString()));
    }
}