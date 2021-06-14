package se.seb.currencyconverter.model;

import java.util.Objects;

public class CurrencyRate {
    private final String currency;
    private final String rate;

    public CurrencyRate(String currency, String rate) {
        super();
        this.currency = currency;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }
    public String getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "CurrencyRate [currency=" + currency + ", rate=" + rate + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRate that = (CurrencyRate) o;
        return Objects.equals(currency, that.currency) && Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, rate);
    }
}
