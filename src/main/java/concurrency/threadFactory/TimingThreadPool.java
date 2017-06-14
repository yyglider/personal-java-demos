package concurrency.threadFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 自定义线程池，通过beforeExecute,afterExecute,termitated等方法来添加日志
 * 记录和统计信息收集。
 *
 * Created by 006564 on 2017/5/31.
 */
public class TimingThreadPool extends ThreadPoolExecutor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private final AtomicLong tasksNum = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r){
        super.beforeExecute(t,r);
        System.out.println(String.format("thread : %s, start : %s",t.getName(),System.nanoTime()));
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r,Throwable t){
        try{
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            tasksNum.incrementAndGet();
            totalTime.addAndGet(taskTime);
            System.out.println(String.format("thread : %s, end : %s", Thread.currentThread().getName(),System.nanoTime()));

        }finally {
            super.afterExecute(r,t);
        }
    }

    @Override
    protected void terminated(){
        try {
            System.out.println(String.format("teminated : avg time = %dns",
                    this.totalTime.get() / tasksNum.get()));
        }finally {
            super.terminated();
        }
    }

    public static void main(String[] args) {
        //构造线程池
        TimingThreadPool pool = new TimingThreadPool(1,1,0L, TimeUnit.MILLISECONDS,
               new LinkedBlockingQueue<>());
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        //提交线程任务
        for (int i = 0; i < 10; i++) {
            Runnable r = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            MyAppThread thread = new MyAppThread(r,"pool-xxx");
            thread.setDebug(true);
            pool.submit(thread);
        }

        System.out.println("begin shutdown");
        pool.shutdown();
        System.out.println("no more tasks!");
    }
}
