package main.java.concurrency.forkJoin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by yaoyuan on 2016/12/12.
 * 计算数列求和
 * */

public class CounterTask extends RecursiveTask<Long> {

    private static final int THRESHHOLD = 10000;
    private long start;
    private long end;

    public CounterTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean canCompute = (end - start)<THRESHHOLD;
        if(canCompute){
            for (long i = start; i < end; i++) {
                sum += i;
            }
        }else {
            // 分成100个小任务
            long step = (start + end )/100;
            ArrayList<CounterTask> subTasks = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;
                if(lastOne>end)
                    lastOne=end;
                CounterTask subTask = new CounterTask(pos,lastOne);
                pos += step+1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for(CounterTask task:subTasks){
                sum+=task.join();
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        CounterTask task = new CounterTask(0,20000L);
        ForkJoinTask<Long> result = pool.submit(task);
        try{
            long res = result.get();
            System.out.println("sum="+res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
