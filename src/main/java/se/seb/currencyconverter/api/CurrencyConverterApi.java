package se.seb.currencyconverter.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.seb.currencyconverter.model.CurrencyRate;
import se.seb.currencyconverter.service.CurrencyConverterService;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("api")
public class CurrencyConverterApi {
    private final CurrencyConverterService currencyConverterService;

    @Inject
    public CurrencyConverterApi(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    @GetMapping(value="/currencies",
            produces={MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<CurrencyRate>> getCurrencies() {
        return ResponseEntity.ok(currencyConverterService.getTodaysCurrencies());
    }

}
