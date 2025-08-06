package org.example.coindesk.dto;

import java.util.List;

public class CoindeskResponse {
    private String updatedTime;
    private List<CurrencyInfo> currencies;

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public List<CurrencyInfo> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<CurrencyInfo> currencies) {
        this.currencies = currencies;
    }
}
