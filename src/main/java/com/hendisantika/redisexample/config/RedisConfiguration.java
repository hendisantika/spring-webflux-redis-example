package com.hendisantika.redisexample.config;

import com.hendisantika.redisexample.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by IntelliJ IDEA.
 * Project : redis-example
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/29/23
 * Time: 09:30
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class RedisConfiguration {

    // No connection information is needed when running a standalone Redis instance on port 6379. A default connection factory
    // bean is created under the covers by spring data.

    @Bean
    public ReactiveRedisOperations<String, Product> redisOperations(ReactiveRedisConnectionFactory factory) {
        final Jackson2JsonRedisSerializer<Product> serializer = new Jackson2JsonRedisSerializer<>(Product.class);
        final RedisSerializationContext.RedisSerializationContextBuilder<String, Product> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        final RedisSerializationContext<String, Product> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
