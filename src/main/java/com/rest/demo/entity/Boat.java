package com.rest.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@RedisHash("boats")
@Getter
@Setter
public class Boat implements Serializable {

    @Id
    private String id;
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @NotNull
    private Engine engine;

    private String type;

}
