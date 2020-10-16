package com.threadtest.demo.sss3.theadpooltest2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.*;

@Service
public class ExecuteImplTest1 {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteImplTest1.class);

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    /***
     * 无返回值的任务调用execute()方法，
     * 无返回值，在定时器中可能出现子线程卡死的情况
     */
    public void testExecuteMethod(){
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                logger.info("执行线程！");
            }
        });
    }

    /***
     * 有返回值的任务使用submit()方法
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public Object testSubmitMethod() throws ExecutionException, InterruptedException, TimeoutException {
        Future<User> future = taskExecutor.submit(new Callable<User>() {
            User user = new User();
            @Override
            public User call() throws Exception {
                return user;
            }
        });
        //线程阻塞等待30秒，返回obj
        return future.get(30, TimeUnit.SECONDS);
    }

}
