package concurrency.Lock.ReadWriteLock;

/**
 * Created by yaoyuan on 2016/6/21.
 */
public class Client {

    public static void main(String[] args) {
        Data data = new Data(10);

        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();

        new WriterThread(data, "ABCDEFGHI").start();
        new WriterThread(data, "012345789").start();
    }
}
