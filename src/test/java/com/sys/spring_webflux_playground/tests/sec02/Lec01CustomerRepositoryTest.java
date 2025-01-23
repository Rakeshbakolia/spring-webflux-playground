package com.sys.spring_webflux_playground.tests.sec02;

import com.sys.spring_webflux_playground.sec02.entity.Customer;
import com.sys.spring_webflux_playground.sec02.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@Slf4j
public class Lec01CustomerRepositoryTest extends AbstractTest{

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findAll(){
        this.customerRepository.findAll().doOnNext(data -> log.info("{}", data))
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById(){
        this.customerRepository.findById(2)
                .doOnNext(data -> log.info("{}", data))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("mike", c.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByName(){
        this.customerRepository.findByName("jake")
                .doOnNext(data -> log.info("{}", data))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("jake@gmail.com", c.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByEmailEndingWith(){
        this.customerRepository.findByEmailEndingWith("ke@gmail.com")
                .doOnNext(data -> log.info("{}", data))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("mike@gmail.com", c.getEmail()))
                .assertNext(c -> Assertions.assertEquals("jake@gmail.com", c.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void insertAndDeleteCustomer(){
        var customer = new Customer();
        customer.setName("rakesh");
        customer.setEmail("rakesh@gmail.com");
        this.customerRepository.save(customer)
                .doOnNext(data -> log.info("{}", data))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertNotNull(c.getId()))
                .expectComplete()
                .verify();

        // count
        this.customerRepository.count()
                .as(StepVerifier::create)
                .expectNext(11L)
                .expectComplete()
                .verify();

        // delete
        this.customerRepository.deleteById(customer.getId())
                .then(this.customerRepository.count())
                .doOnNext(data -> log.info("{}", data))
                .as(StepVerifier::create)
                .expectNext(10L)
                .expectComplete()
                .verify();

    }

    @Test
    public void updateCustomer(){
        this.customerRepository.findByName("ethan")
                .doOnNext(customer -> customer.setName("rakesh"))
                .flatMap(customer -> this.customerRepository.save(customer))
                .doOnNext(data -> log.info("{}", data))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("rakesh", c.getName()))
                .expectComplete()
                .verify();
    }

}
