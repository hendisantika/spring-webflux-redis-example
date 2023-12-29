package com.hendisantika.redisexample.data;

import com.hendisantika.redisexample.model.Product;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Created by IntelliJ IDEA.
 * Project : redis-example
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/29/23
 * Time: 09:33
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductLoader {
    private final ReactiveRedisConnectionFactory connFactory;
    private final ReactiveRedisOperations<String, Product> productOps;

    @PostConstruct
    public void loadData() {
        connFactory.getReactiveConnection()
                .serverCommands()
                .flushAll()
                .thenMany(Flux.range(1, 10)
                        .map(this::generateProduct)
                        .flatMap(product -> productOps.opsForValue().set(product.getId(), product))
                )
                .doOnSubscribe(subscription -> log.info("Loading test data..."))
                .doOnComplete(() -> log.info("Test data load complete!"))
                .subscribe();
    }
}
