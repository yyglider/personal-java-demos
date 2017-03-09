package main.java.concurrency.book.thread;

public class PriorityDemo {
    public static class High extends Thread{
        static int cnt = 0;
        public void run(){
            while(true){
                synchronized (PriorityDemo.class){
                    cnt ++ ;
                    if(cnt>100000){
                        System.out.println("high priority is complete");
                        break;
                    }
                }
            }
        }
    }

    public static class Low extends Thread{
        static int cnt = 0;
        public void run(){
            while(true){
                synchronized (PriorityDemo.class){
                    cnt ++ ;
                    if(cnt>100000){
                        System.out.println("low priority is complete");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        High high = new High();
        Low low = new Low();
        high.setPriority(10);
        low.setPriority(1);
        high.start();
        low.start();
    }
}
