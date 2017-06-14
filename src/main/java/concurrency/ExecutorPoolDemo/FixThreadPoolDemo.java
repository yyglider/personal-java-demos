package concurrency.ExecutorPoolDemo;

import java.util.concurrent.*;


public class FixThreadPoolDemo {
    // Runnable , 无返回值
    public static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("hello " + Thread.currentThread().getName());
        }
    }

    //Callable，有返回值
    public static class ReturnTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "hello" + Thread.currentThread().getName();
        }
    }

    public static void main(String[] args) {
        //创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
        //线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
        ExecutorService pool = Executors.newFixedThreadPool(2);
//        //创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
//        Task t = new Task();
//        for (int i = 0; i < 10; i++) {
//            pool.submit(t);
//        }
        ReturnTask task = new ReturnTask();
        Future<String> futrue = pool.submit(task);
        try {
            String returnStr = futrue.get();
            System.out.println(returnStr);
        } catch (InterruptedException e) {
            //重新设置线程的中断状态
            Thread.currentThread().interrupt();
            //取消任务
            futrue.cancel(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //关闭线程池
        pool.shutdown();
    }
}
