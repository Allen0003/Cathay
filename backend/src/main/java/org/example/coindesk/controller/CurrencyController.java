package org.example.coindesk.controller;

import org.example.coindesk.entity.Currency;
import org.example.coindesk.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Currency> getByCode(@PathVariable String code) {
        return currencyService.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Currency create(@RequestBody Currency currency) {
        return currencyService.create(currency);
    }

    @PutMapping("/{code}")
    public Currency update(@PathVariable String code, @RequestBody Currency currency) {
        currency.setCode(code);
        return currencyService.update(code, currency);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        currencyService.delete(code);
    }
}
