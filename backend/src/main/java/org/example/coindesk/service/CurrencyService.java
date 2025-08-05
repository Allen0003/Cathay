package org.example.coindesk.service;

import org.example.coindesk.entity.Currency;
import org.example.coindesk.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> findByCode(String code) {
        return currencyRepository.findById(code);
    }

    public Currency create(Currency currency) {
        return currencyRepository.save(currency);
    }

    public Currency update(String code, Currency currency) {
        if (!currencyRepository.existsById(code)) {
            throw new RuntimeException("Currency not found");
        }
        return currencyRepository.save(currency);
    }

    public void delete(String code) {
        currencyRepository.deleteById(code);
    }
}
