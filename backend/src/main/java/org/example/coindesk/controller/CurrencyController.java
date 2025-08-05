package org.example.coindesk.controller;

import org.example.coindesk.entity.Currency;
import org.example.coindesk.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public List<Currency> getAll() {
        return currencyService.findAll();
    }

    @GetMapping("/{code}")
    public Currency getByCode(@PathVariable String code) {
        return currencyService.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Currency not found"));
    }

    @PostMapping
    public Currency create(@RequestBody Currency currency) {
        return currencyService.create(currency);
    }

    @PutMapping("/{code}")
    public Currency update(@PathVariable String code, @RequestBody Currency currency) {
        currency.setCode(code);
        return currencyService.update(code, currency);
    }

    @DeleteMapping("/{code}")
    public void delete(@PathVariable String code) {
        currencyService.delete(code);
    }
}
