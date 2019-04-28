package com.safesoft.springboot.basessm.entity;

import lombok.*;

/**
 * @author jay.zhou
 * @date 2019/4/26
 * @time 14:39
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductRecord {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品编号
     */
    private String productNo;

    /**
     * 抢到单子的用户id
     */
    private int userId;

    public ProductRecord(String productNo, int userId) {
        this.productNo = productNo;
        this.userId = userId;
    }
}
