package com.safesoft.rabbitmq08.entity;

import lombok.*;

/**
 * @author jay.zhou
 * @date 2019/5/5
 * @time 16:54
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogEntity {

    /**
     * 当前操作的方法名
     */
    private String methodName;

    /**
     * 操作模块
     */
    private String moduleName;
}
