package concurrency.ExecutorPoolDemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MyThreadPool {

    private Worker[] workers;

    //使用阻塞队列
    private BlockingQueue<Runnable> tasks = new LinkedBlockingDeque<>();

    public MyThreadPool(int size) {
        this.workers = new Worker[size];
    }

    public void execute(Runnable task){
        tasks.add(task);
    }

    public void start(){
        for(int i=0;i<workers.length;i++) {
            workers[i] = new Worker();
            workers[i].start();
        }

    }

    public void destroy(){
        for(Worker worker:workers){
            worker.stopIt();
            worker = null;
        }
    }

    /**
     * 工作执行者
     */
    private class Worker extends Thread{

        private volatile boolean isRunning = true;

        public void stopIt(){
            isRunning =false;
        }
        @Override
        public void run() {
            while (isRunning){
                try {
                    Runnable task=tasks.take();
                    task.run();
                    tasks.remove(task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}