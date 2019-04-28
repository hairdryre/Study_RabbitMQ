package com.safesoft.springboot.basessm.entity;

import lombok.*;

/**
 * @author jay.zhou
 * @date 2019/4/26
 * @time 14:36
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    private Long id;

    /**
     * 商品编号
     */
    private String productNo;

    /**
     * 商品库存
     */
    private int total;
}
