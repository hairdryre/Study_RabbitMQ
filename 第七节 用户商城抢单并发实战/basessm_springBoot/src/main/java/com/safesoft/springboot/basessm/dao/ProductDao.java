package com.safesoft.springboot.basessm.dao;

import com.safesoft.springboot.basessm.entity.Product;
import com.safesoft.springboot.basessm.entity.ProductRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author jay.zhou
 * @date 2019/4/26
 * @time 14:48
 */
@Repository
@Mapper
public interface ProductDao {

    /**
     * 根据商品编号寻找商品
     *
     * @param productNo 商品编号
     * @return 商品实体
     */
    Product selectProductByNo(@Param("productNo") String productNo);

    /**
     * 更新商品表，把商品表的库存减少1
     *
     * @param productNo 商品编号
     * @return 成功返回1，失败返回0
     */
    int updateProduct(@Param("productNo") String productNo);

    /**
     * 插入抢单记录
     *
     * @param record 抢单记录实体
     * @return 成功返回1，失败返回0
     */
    int insertProductRecord(ProductRecord record);
}
