package com.safesoft.rabbitmq08.service;

import com.safesoft.rabbitmq08.entity.LogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author jay.zhou
 * @date 2019/5/5
 * @time 17:19
 */
@Service
public class LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    public Boolean insertLog(LogEntity logEntity) throws InterruptedException {
        LOGGER.info("插入系统日志", logEntity);

        //插入系统日志需要花费很多秒，模拟做一些数据统计业务
        Thread.sleep(3000);

        return Boolean.TRUE;
    }
}
