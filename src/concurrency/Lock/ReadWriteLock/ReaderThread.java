package concurrency.Lock.ReadWriteLock;

/**
 * Created by yaoyuan on 2016/6/21.
 */
public class ReaderThread extends Thread {

    private final Data data;

    public ReaderThread(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (true) {
            String result = null;
            try {
                result = data.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " => " + result);
        }
    }
}