package uk.co.omniolytics.data.streams.config;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
@EnableDiscoveryClient
@XSlf4j
public class CloudConfig extends AbstractCloudConfig {
    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        return connectionFactory().rabbitConnectionFactory("omnio-mq");
    }
}
