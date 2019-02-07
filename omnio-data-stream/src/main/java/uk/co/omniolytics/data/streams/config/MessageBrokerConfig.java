package uk.co.omniolytics.data.streams.config;

import java.util.Arrays;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageBrokerConfig
{
    @Value("${rabbitmq.user.queuename}")
    private String queuename;
    @Value("${rabbitmq.user.exchange.name}")
    private String topicExchangeName;

    public MessageBrokerConfig() {}

    @Bean
    public java.util.List<Declarable> topicBindings()
    {
        Queue topicQueue = new Queue(queuename, true);
        TopicExchange topicExchange = new TopicExchange(topicExchangeName);
        return Arrays.asList(topicQueue,
            BindingBuilder.bind(topicQueue).to(topicExchange).with("#"));
    }

    @Bean
    public SimpleRabbitListenerContainerFactory container(ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer)
    {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
