package com.safesoft.springboot.basessm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * @author jay.zhou
 * @date 2019/4/24
 * @time 17:34
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long id;

    private String username;

    private int age;

    public UserEntity(String username, int age) {
        this.username = username;
        this.age = age;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("age", age)
                .toString();
    }
}
