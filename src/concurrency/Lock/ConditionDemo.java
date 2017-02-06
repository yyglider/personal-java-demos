package concurrency.Lock;

/**
 *
 * 条件锁只是一个帮助用户理念的概念，实际上并没有条件锁这种锁。对于每个重入锁，都可以通过newCondition()方法绑定若干个条件对象。

 条件对象提供以下方法以实现不同的等待语义
 await() 调用该方法的前提是，当前线程已经成功获得与该条件对象绑定的重入锁，否则调用该方法时会抛出IllegalMonitorStateException。
 调用该方法外，当前线程会释放当前已经获得的锁（这一点与Java内置锁的wait方法一致），并且等待其它线程调用该条件对象的signal()或者signalAll()方法.
 （这一点与Java内置锁wait后等待notify()或notifyAll()很像）。或者在等待期间，当前线程被打断，则wait()方法会抛出InterruptedException并清除当前线程的打断状态。

 await(long time, TimeUnit unit) 适用条件和行为与await()基本一致，唯一不同点在于，
 指定时间之内没有收到signal()或signalALL()信号或者线程中断时该方法会返回false;其它情况返回true。

 awaitNanos(long nanosTimeout) 调用该方法的前提是，当前线程已经成功获得与该条件对象绑定的重入锁，否则调用该方法时会抛出IllegalMonitorStateException。
 nanosTimeout指定该方法等待信号的的最大时间（单位为纳秒）。若指定时间内收到signal()或signalALL()则返回nanosTimeout减去已经等待的时间；
 若指定时间内有其它线程中断该线程，则抛出InterruptedException并清除当前线程的打断状态；
 若指定时间内未收到通知，则返回0或负数。

 awaitUninterruptibly() 调用该方法的前提是，当前线程已经成功获得与该条件对象绑定的重入锁，否则调用该方法时会抛出IllegalMonitorStateException。调用该方法后，结束等待的唯一方法是其它线程调用该条件对象的signal()或signalALL()方法。等待过程中如果当前线程被中断，该方法仍然会继续等待，同时保留该线程的中断状态。
 awaitUntil(Date deadline) 适用条件与行为与awaitNanos(long nanosTimeout)完全一样，唯一不同点在于它不是等待指定时间，而是等待由参数指定的某一时刻。
 调用条件等待的注意事项

 调用上述任意条件等待方法的前提都是当前线程已经获得与该条件对象对应的重入锁。调用条件等待后，当前线程让出CPU资源。
 上述等待方法结束后，方法返回的前提是它能重新获得与该条件对象对应的重入锁。如果无法获得锁，仍然会继续等待。
 这也是awaitNanos(long nanosTimeout)可能会返回负值的原因。
 一旦条件等待方法返回，则当前线程肯定已经获得了对应的重入锁。

 重入锁可以创建若干个条件对象，signal()和signalAll()方法只能唤醒相同条件对象的等待。
 一个重入锁上可以生成多个条件变量，不同线程可以等待不同的条件，从而实现更加细粒度的的线程间通信。

 signal()与signalAll()
 signal() 若有一个或若干个线程在等待该条件变量，则该方法会唤醒其中的一个（具体哪一个，无法预测）。调用该方法的前提是当前线程持有该条件变量对应的锁，否则抛出IllegalMonitorStateException。
 signalALL() 若有一个或若干个线程在等待该条件变量，则该方法会唤醒所有等待。调用该方法的前提是当前线程持有该条件变量对应的锁，否则抛出IllegalMonitorStateException。
 */
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

    public static void main(String[] args) throws InterruptedException {
        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println(new Date() + "\tThread 1 is waiting");
                    try {
                        long waitTime = condition.awaitNanos(TimeUnit.SECONDS.toNanos(2));
                        System.out.println(new Date() + "\tThread 1 remaining time " + waitTime);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(new Date() + "\tThread 1 is waken up");
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println(new Date() + "\tThread 2 is running");
                    try {
                        Thread.sleep(4000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    //虽然thread 2一开始就调用了signal()方法去唤醒thread 1，但是因为thread 2在4秒钟后才释放锁，
                    // 也即thread 1在4秒后才获得锁，所以thread 1的await方法在4秒钟后才返回，并且返回负值。
                    condition.signal();
                    System.out.println(new Date() + "\tThread 2 ended");
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}
