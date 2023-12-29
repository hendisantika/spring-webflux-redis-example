package com.hendisantika.redisexample.service;

import com.hendisantika.redisexample.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : redis-example
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/29/23
 * Time: 09:36
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

}
