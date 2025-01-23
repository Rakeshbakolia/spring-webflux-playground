package com.sys.spring_webflux_playground.tests.sec02;

import com.sys.spring_webflux_playground.sec02.repository.CustomerOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
public class Lec03CustomerOrderRepositoryTest {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Test
    public void productOrderByCustomer(){
        this.customerOrderRepository.getProductOrderedByCustomer("mike")
                .doOnNext(data -> log.info("{}", data))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    public void orderDetailsByProduct(){
        this.customerOrderRepository.getOrderDetailsByProduct("iphone 20")
                .doOnNext(dto -> log.info("{}", dto))
                .as(StepVerifier::create)
                .assertNext(dto -> Assertions.assertEquals(975, dto.amount()))
                .assertNext(dto -> Assertions.assertEquals(950, dto.amount()))
                .expectComplete()
                .verify();
    }
}
