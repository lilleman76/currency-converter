package se.seb.currencyconverter;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope("singleton")
public class Properties {
    private PropertiesConfiguration configuration;

    @Bean
    @ConditionalOnProperty(name = "spring.config.location", matchIfMissing = false)
    public PropertiesConfiguration propertiesConfiguration(
            @Value("${spring.config.location}") String path) throws Exception {
        configuration = new PropertiesConfiguration(path);
        configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
        return configuration;
    }

    public String getProperty(String key) {
        return (String) configuration.getProperty(key);
    }
}
