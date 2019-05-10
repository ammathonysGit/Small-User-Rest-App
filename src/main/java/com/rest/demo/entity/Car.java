package com.rest.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@RedisHash("cars")
@Getter
@Setter
public class Car implements Serializable {

    @Id
    private String id;
    @NotNull(message = "Brand cannot be null")
    private String brand;
    @NotNull(message = "Model cannot be null")
    private String model;
    private String colour;
    private Integer horsePower;

}
