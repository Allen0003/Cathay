package org.example.coindesk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.coindesk.dto.CoindeskResponse;
import org.example.coindesk.dto.CurrencyInfo;
import org.example.coindesk.entity.Currency;
import org.example.coindesk.repository.CurrencyRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CoindeskService {

    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;
    private final ObjectMapper objectMapper;

    public CoindeskService(RestTemplateBuilder builder, CurrencyRepository currencyRepository) {
        this.restTemplate = builder.build();
        this.currencyRepository = currencyRepository;
        this.objectMapper = new ObjectMapper();
    }

    public String getRawData() {
        String url = "https://kengp3.github.io/blog/coindesk.json";
        return restTemplate.getForObject(url, String.class);
    }

    public CoindeskResponse getConvertedData() throws Exception {
        String json = getRawData();
        JsonNode root = objectMapper.readTree(json);

        // 轉換時間格式
        String updatedISO = root.path("time").path("updatedISO").asText();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = inputFormat.parse(updatedISO);
        String formattedTime = outputFormat.format(date);

        // 幣別資料
        JsonNode bpiNode = root.path("bpi");

        List<CurrencyInfo> currencies = new ArrayList<>();

        Iterator<String> fieldNames = bpiNode.fieldNames();

        while (fieldNames.hasNext()) {
            String code = fieldNames.next();
            JsonNode currencyNode = bpiNode.path(code);
            double rate = currencyNode.path("rate_float").asDouble();

            // 從 DB 查中文名稱
            Optional<Currency> currencyOpt = currencyRepository.findById(code);
            String name = currencyOpt.map(Currency::getName).orElse("未知");

            CurrencyInfo info = new CurrencyInfo();
            info.setCode(code);
            info.setName(name);
            info.setRate(rate);

            currencies.add(info);
        }

        CoindeskResponse response = new CoindeskResponse();
        response.setUpdatedTime(formattedTime);
        response.setCurrencies(currencies);

        return response;
    }
}

