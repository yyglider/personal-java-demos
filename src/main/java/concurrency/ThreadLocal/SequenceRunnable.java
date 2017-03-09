package main.java.concurrency.ThreadLocal;

/**
 * Created by yaoyuan on 2017/3/3.
 */
public class SequenceRunnable implements Runnable {

    private Sequence sequence;

    public SequenceRunnable(Sequence sequence){
        this.sequence = sequence;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "=>" + sequence.getNumber() );
        }
    }
}
