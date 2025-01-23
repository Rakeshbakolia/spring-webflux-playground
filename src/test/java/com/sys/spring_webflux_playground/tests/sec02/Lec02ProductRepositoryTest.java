package com.sys.spring_webflux_playground.tests.sec02;

import com.sys.spring_webflux_playground.sec02.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

@SpringBootTest
@Slf4j
public class Lec02ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findByPrinceRange(){
        this.productRepository.findByPriceBetween(750,1000)
                .doOnNext(data -> log.info("{}", data))
                .as(StepVerifier::create)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByPageable(){
        this.productRepository.findBy(PageRequest.of(0, 3).withSort(Sort.by("price").ascending()))
                .doOnNext(data -> log.info("{}", data))
                .as(StepVerifier::create)
                .assertNext(data -> Assertions.assertEquals(200, data.getPrice()))
                .assertNext(data -> Assertions.assertEquals(250, data.getPrice()))
                .assertNext(data -> Assertions.assertEquals(300, data.getPrice()))

                .expectComplete()
                .verify();
    }
}
