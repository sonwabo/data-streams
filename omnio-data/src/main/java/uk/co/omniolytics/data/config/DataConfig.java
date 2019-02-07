package uk.co.omniolytics.data.config;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@XSlf4j
@ComponentScan(basePackages = "uk.co.omniolytics.data.impl")
public class DataConfig {
    @Bean
    public CloudantClient cloudantClient(Environment environment) throws MalformedURLException {
        log.entry(environment);
        final String uri = environment.getRequiredProperty("cloudant.uri");
        log.info("Creating CloudantClient:{}", uri);
        CloudantClient client = ClientBuilder
                .url(new URL(uri))
                .username(environment.getRequiredProperty("cloudant.username"))
                .password(environment.getRequiredProperty("cloudant.password"))
                .build();
        return log.exit(client);
    }

    @Bean
    public Database database(CloudantClient client, Environment environment) {
        final String databaseName = environment.getRequiredProperty("cloudant.database");
        log.info("Connecting to database:{}", databaseName);
        Database database = client.database(databaseName, true);
        log.info("Connected to:{}", database.info());
        return database;
    }
}
