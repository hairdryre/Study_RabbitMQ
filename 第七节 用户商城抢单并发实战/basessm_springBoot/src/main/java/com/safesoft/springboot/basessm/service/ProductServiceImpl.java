package com.safesoft.springboot.basessm.service;

import com.safesoft.springboot.basessm.dao.ProductDao;
import com.safesoft.springboot.basessm.entity.Product;
import com.safesoft.springboot.basessm.entity.ProductRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jay.zhou
 * @date 2019/4/26
 * @time 14:58
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private static final String PRODUCT_NO = "No123321";

    @Autowired
    private ProductDao productDao;

    /**
     * 抢单方法实现
     *
     * @param userId 抢单用户id
     */
    @Override
    public void robbingProduct(int userId) {
/*        //先查询商品
        Product product = productDao.selectProductByNo(PRODUCT_NO);
        if (product != null && product.getTotal() > 0) {
            //原因：多个线程可能同时进入此方法体
            //再更新库存表
            productDao.updateProduct(PRODUCT_NO);
            //插入记录
            productDao.insertProductRecord(new ProductRecord(PRODUCT_NO, userId));
            //发送短信
            LOGGER.info("用户{}抢单成功", userId);
        } else {
            LOGGER.error("用户{}抢单失败", userId);
        }*///v1.0，有线程安全问题。需要修改SQL与代码

        try {
            Product product = productDao.selectProductByNo(PRODUCT_NO);
            if (product != null && product.getTotal() > 0) {
                //更新库存表，库存量减少1。返回1说明更新成功。返回0说明库存已经为0
                int updateResult = productDao.updateProduct(PRODUCT_NO);
                if (updateResult > 0) {
                    //插入记录
                    productDao.insertProductRecord(new ProductRecord(PRODUCT_NO, userId));
                    //发送短信
                    LOGGER.info("用户{}抢单成功", userId);
                } else {
                    LOGGER.error("用户{}抢单失败", userId);
                }
            } else {
                LOGGER.error("用户{}抢单失败", userId);
            }
        } catch (Exception e) {
            LOGGER.error("处理抢单业务出现异常 :{}", e.getMessage());
        }

    }
}
