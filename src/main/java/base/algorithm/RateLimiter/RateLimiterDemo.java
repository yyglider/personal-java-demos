package base.algorithm.RateLimiter;

import com.google.common.util.concurrent.RateLimiter;
import concurrency.threadFactory.TimingThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * RateLimiter是guava提供的基于令牌桶算法的实现类，可以非常简单的完成限流特技，并且根据系统的实际情况来调整生成token的速率。
 */
public class RateLimiterDemo {

    public static void main(String[] args) {
        test();
    }


    public static  void test(){


        //构造线程池
        TimingThreadPool pool = new TimingThreadPool(10,20,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());


        List<Runnable> tasks = new ArrayList<>();

        //创建线程任务
        for (int i = 0; i < 10; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is running");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            tasks.add(r);
        }
        //每秒最多放入1个令牌
        RateLimiter rateLimiter = RateLimiter.create(1);

        long start = System.nanoTime();
        for (Runnable task : tasks) {
            // may wait, acquire 2 token，每2秒才能执行一个任务
            // System.out.println("等待时间" + rateLimiter.acquire(2));


            //一旦判断出在timeout时间内还无法取得令牌，就返回fals
            System.out.println("是否可以获取：" + rateLimiter.tryAcquire());


            pool.submit(task);
        }
        System.out.println(String.format("cost : %s",System.nanoTime() - start));




        System.out.println("begin shutdown");
        pool.shutdown();
        System.out.println("no more tasks!");
    }
}




