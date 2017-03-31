package main.java.concurrency.ExecutorPoolDemo;


public class MyThread extends Thread {
    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName()+" is running ... ");
    }
}
