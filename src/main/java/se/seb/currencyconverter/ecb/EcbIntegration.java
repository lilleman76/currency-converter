package se.seb.currencyconverter.ecb;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import se.seb.currencyconverter.utils.HttpClient;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@Component
public class EcbIntegration {
    private final HttpClient httpClient;

    @Inject
    public EcbIntegration(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Cacheable("ecbdata")
    public String requestDataFromECB() {
        return httpClient.requestData("https://www.ecb.europa.eu",
                "/stats/eurofxref/eurofxref-daily.xml", MediaType.TEXT_XML);
    }
}
