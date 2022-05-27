package com.zqz.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SpringBootMqConfig
 * @Date: Created in 9:16 2022/5/26
 */
@Configuration
public class SpringBootMqConfig {
    private final static String QUEUE_NAME = "ZQZ_TEST_QUEUE";
    private final static String EXCHANGE = "ZQZ_TEST2_EXCHANGE";
    private final static String EXCHANGE_ROUTING_KEY = "TEST_KEY";


    @Bean
    public TopicExchange createTopicExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }


    @Bean
    public Queue createQueue() {
        return new Queue(QUEUE_NAME, true, false, false);
    }

    @Bean
    public Binding createBinding() {
        return BindingBuilder.bind(createQueue()).to(createTopicExchange()).with(EXCHANGE_ROUTING_KEY);
    }

}
