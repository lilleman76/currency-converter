package se.seb.currencyconverter.utils;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HttpClient {

    @Singleton
    private final Client client;

    public HttpClient() {
        var config = new ClientConfig();
        config.property(ClientProperties.CONNECT_TIMEOUT, 200000);
        config.property(ClientProperties.READ_TIMEOUT, 200000);
        client = JerseyClientBuilder.createClient(config).register(logging());
    }

    LoggingFeature logging() {
        var logger = Logger.getLogger(this.getClass().getName());
        return new LoggingFeature(logger, Level.INFO, null, null);
    }


    public String requestData(String target, String path, String type) {

        var builder = client
                .target(target)
                .path(path)
                .request(type);

        return builder.get().readEntity(String.class);
    }
}

