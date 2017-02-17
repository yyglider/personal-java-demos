package concurrency.book.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by yaoyuan on 2016/12/7.
 */
public class SemaphoreDemo implements Runnable{

    final Semaphore semp = new Semaphore(5);

    @Override
    public void run() {
        try{
            semp.acquire();
            //模拟耗时操作
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getId()+" : done!");
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ExecutorService exec = Executors.newFixedThreadPool(10);
        final SemaphoreDemo demo = new SemaphoreDemo();
        for(int i=0;i<20;i++){
            exec.submit(demo);
        }
    }
}
