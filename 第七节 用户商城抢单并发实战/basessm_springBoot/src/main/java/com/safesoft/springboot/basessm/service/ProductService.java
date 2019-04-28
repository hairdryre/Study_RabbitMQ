package com.safesoft.springboot.basessm.service;

/**
 * @author jay.zhou
 * @date 2019/4/26
 * @time 14:53
 */
public interface ProductService {

    /**
     * 开始抢单
     * @param userId 抢单的用户id
     */
    void robbingProduct(int userId);
}
