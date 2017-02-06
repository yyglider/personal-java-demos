package concurrency.Lock;

/**
 * Created by yaoyuan on 2016/6/21.
 */
/**
 * 而原子性更多是针对写操作而言。对于读多写少的场景，一个读操作无须阻塞其它读操作，只需要保证读和写或者写与写不同时发生即可。
 * 此时，如果使用重入锁（即排它锁），对性能影响较大。Java中的读写锁（ReadWriteLock）就是为这种读多写少的场景而创造的。
 *
 * 通过readLock()和writeLock()方法可分别获得读锁实例和写锁实例，并通过Lock接口提供的获取锁方法获得对应的锁。
 读写锁的锁定规则如下：
 获得读锁后，其它线程可获得读锁而不能获取写锁
 获得写锁后，其它线程即不能获得读锁也不能获得写锁
 private final Lock lock = new ReentrantLock();

     public void foo() {
         lock.lock();
         try {
            ...
         } finally {
            lock.unlock();
         }
     }
 执行结果如下
 Sat Jun 18 21:33:46 CST 2016  Thread 1 started with read lock
 Sat Jun 18 21:33:46 CST 2016  Thread 2 started with read lock
 Sat Jun 18 21:33:48 CST 2016  Thread 2 ended
 Sat Jun 18 21:33:48 CST 2016  Thread 1 ended
 Sat Jun 18 21:33:48 CST 2016  Thread 3 started with write lock
 Sat Jun 18 21:33:50 CST 2016  Thread 3 ended

 从上面的执行结果可见，thread 1和thread 2都只需获得读锁，因此它们可以并行执行。
 而thread 3因为需要获取写锁，必须等到thread 1和thread 2释放锁后才能获得锁。

 */

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                readWriteLock.readLock().lock();
                try {
                    System.out.println(new Date() + "\tThread 1 started with read lock");
                    try {
                        Thread.sleep(2000);
                    } catch (Exception ex) {
                    }
                    System.out.println(new Date() + "\tThread 1 ended");
                } finally {
                    readWriteLock.readLock().unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                readWriteLock.readLock().lock();
                try {
                    System.out.println(new Date() + "\tThread 2 started with read lock");
                    try {
                        Thread.sleep(2000);
                    } catch (Exception ex) {
                    }
                    System.out.println(new Date() + "\tThread 2 ended");
                } finally {
                    readWriteLock.readLock().unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Lock lock = readWriteLock.writeLock();
                lock.lock();
                try {
                    System.out.println(new Date() + "\tThread 3 started with write lock");
                    try {
                        Thread.sleep(2000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(new Date() + "\tThread 3 ended");
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}