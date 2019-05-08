package com.rest.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@RedisHash("USER")
@Getter
@Setter
public class User implements Serializable {

    private String id;
    @NotNull(message = "Name cannot be NULL")
    private String name;
    @NotNull(message = "Surname cannot be NULL")
    private String surname;
    @NotNull(message = "E-mail cannot be NULL")
    private String email;

}
