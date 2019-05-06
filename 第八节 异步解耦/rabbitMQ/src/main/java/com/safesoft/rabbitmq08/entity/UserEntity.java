package com.safesoft.rabbitmq08.entity;

import lombok.*;

/**
 * @author jay.zhou
 * @date 2019/5/5
 * @time 17:15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {

    private Long id;

    private String username;
}
