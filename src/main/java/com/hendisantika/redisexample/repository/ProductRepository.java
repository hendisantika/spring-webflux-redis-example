package com.hendisantika.redisexample.repository;

import com.hendisantika.redisexample.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : redis-example
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/29/23
 * Time: 09:31
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Component
public class ProductRepository {
    private final ReactiveRedisOperations<String, Product> productOps;
}
