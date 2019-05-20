package com.rest.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@RedisHash("motorcycles")
@Getter
@Setter
public class Motorcycle implements Serializable{
    @Id
    private String id;
    @NotNull(message = "Brand cannot be null")
    private String brand;
    @NotBlank(message = "Type cannot be null or Empty")
    private String type;
    @NotBlank(message = "Model cannot be null or Empty")
    private String model;
    private String colour;
    private Integer horsePower;
}
