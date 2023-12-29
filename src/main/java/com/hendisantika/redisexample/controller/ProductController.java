package com.hendisantika.redisexample.controller;

/**
 * Created by IntelliJ IDEA.
 * Project : redis-example
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/29/23
 * Time: 09:38
 * To change this template use File | Settings | File Templates.
 */

import com.hendisantika.redisexample.model.ProductResponse;
import com.hendisantika.redisexample.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Gets the product data for a specific product.
     *
     * @param id product identifier
     * @return a {@link ProductResponse} if found
     */
    @GetMapping(value = "/products/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductResponse>> getProduct(@PathVariable("id") String id) {
        return productService.getProduct(id)
                .map(product -> ResponseEntity.ok(ProductResponse.from(product)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
