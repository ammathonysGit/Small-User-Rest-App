package com.rest.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@RedisHash("trucks")
@Getter
@Setter
public class Truck implements Serializable {

    @Id
    private String id;
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    private Integer horsePower;
    private String colour;

}
