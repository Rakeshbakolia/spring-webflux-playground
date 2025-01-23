package com.sys.spring_webflux_playground.sec01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("reactive")
@Slf4j
public class ReactiveWebController {

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:7070").build();

    @GetMapping("products")
    public Flux<Product> getProducts() {
        return this.webClient.get().uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(response -> log.info("Retrieved result: {}",response));

    }

    @GetMapping("products/notorious")
    public Flux<Product> getProductsNotorious() {
        return this.webClient.get().uri("/demo01/products/notorious")
                .retrieve()
                .bodyToFlux(Product.class)
                .onErrorComplete()
                .doOnNext(response -> log.info("Retrieved result: {}",response));

    }

    @GetMapping(value = "product/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductStream() {
        return this.webClient.get().uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(response -> log.info("Retrieved result: {}",response));

    }
}
