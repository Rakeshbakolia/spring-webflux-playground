package com.sys.spring_webflux_playground.sec02.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
@Data
public class Customer {
    @Id
    private Integer id;
    private String name;
    private String email;
}
