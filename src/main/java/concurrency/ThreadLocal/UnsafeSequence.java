package main.java.concurrency.ThreadLocal;

/**
 * Created by yaoyuan on 2017/3/3.
 */
public class UnsafeSequence implements Sequence {

    private static int number = 0;

    public int getNumber() {
        number = number + 1;
        return number;
    }

    public static void main(String[] args) {
        SequenceRunnable sequenceRunnable = new SequenceRunnable(new UnsafeSequence());

        Thread thread1 = new Thread(sequenceRunnable);
        Thread thread2 = new Thread(sequenceRunnable);
        Thread thread3 = new Thread(sequenceRunnable);

        thread1.start();
        thread2.start();
        thread3.start();
    }

}
