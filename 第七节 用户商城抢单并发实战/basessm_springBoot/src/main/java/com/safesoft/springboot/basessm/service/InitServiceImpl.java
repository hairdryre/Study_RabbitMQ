package com.safesoft.springboot.basessm.service;

import com.safesoft.springboot.basessm.rabbitMQ.RobbingSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jay.zhou
 * @date 2019/4/26
 * @time 15:16
 */
@Service
public class InitServiceImpl implements InitService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private RobbingSender sender;

    private int threadNum = 1000;

    private static int userId = 0;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void generateMultiThread() {
        //生成threadNum个线程
        ExecutorService pool = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            //执行每个线程的任务
            pool.execute(new RobbingTask(countDownLatch));
        }
        //主线程说：开始!
        countDownLatch.countDown();
    }

    class RobbingTask implements Runnable {

        private CountDownLatch countDownLatch;

        public RobbingTask(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                //当前用户的ID
                int userNo = userId += 1;
                /**
                 * 阻塞住当前线程
                 * 等待主线程提示开抢，主线程调用countDownLatch.countDown();后
                 * 开始执行下面的productService.robbingProduct(userId);
                 */
                countDownLatch.await();
                //当前用户发出抢单指令
//                productService.robbingProduct(userNo);
                //由消息中间件来操作
                sender.sendRobbingMessage(userNo);
            } catch (Exception e) {
                LOGGER.error("an exception was occurred , caused by :{}", e.getMessage());
            }
        }
    }
}
