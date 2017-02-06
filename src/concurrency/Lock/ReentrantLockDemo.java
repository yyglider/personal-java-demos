package concurrency.Lock;

/**
 * Created by yaoyuan on 2016/6/20.
 */

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 Java中的重入锁（即ReentrantLock）与Java内置锁一样，是一种排它锁。使用synchronized的地方一定可以用ReentrantLock代替。

 重入锁需要显示请求获取锁，并显示释放锁。为了避免获得锁后，没有释放锁，而造成其它线程无法获得锁而造成死锁，
 一般建议将释放锁操作放在finally块里，如下所示。
     try{
         renentrantLock.lock();
        // 用户操作
     } finally {
        renentrantLock.unlock();
     }
 如果重入锁已经被其它线程持有，则当前线程的lock操作会被阻塞。除了lock()方法之外，
 重入锁（或者说锁接口）还提供了其它获取锁的方法以实现不同的效果。

 lockInterruptibly() 该方法尝试获取锁，若获取成功立即返回；若获取不成功则阻塞等待。
 与lock方法不同的是，在阻塞期间，如果当前线程被打断（interrupt）则该方法抛出InterruptedException。该方法提供了一种解除死锁的途径。

 tryLock() 该方法试图获取锁，若该锁当前可用，则该方法立即获得锁并立即返回true；
 若锁当前不可用，则立即返回false。该方法不会阻塞，并提供给用户对于成功获利锁与获取锁失败进行不同操作的可能性。

 tryLock(long time, TimeUnit unit) 该方法试图获得锁，若该锁当前可用，则立即获得锁并立即返回true。
 若锁当前不可用，则等待相应的时间（由该方法的两个参数决定）：
 1）若该时间内锁可用，则获得锁，并返回true；
 2）若等待期间当前线程被打断，则抛出InterruptedException；
 3）若等待时间结束仍未获得锁，则返回false。

 =======================================================================================================
 重入锁可定义为公平锁或非公平锁，默认实现为非公平锁。

 公平锁是指多个线程获取锁被阻塞的情况下，锁变为可用时，最新申请锁的线程获得锁。
 可通过在重入锁（RenentrantLock）的构造方法中传入true构建公平锁，
 如Lock lock = new RenentrantLock(true)

 非公平锁是指多个线程等待锁的情况下，锁变为可用状态时，哪个线程获得锁是随机的。

 synchonized相当于非公平锁。可通过在重入锁的构造方法中传入false或者使用无参构造方法构建非公平锁。

 =========================================================================================================
 */

public class ReentrantLockDemo implements Runnable{

    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for(int j=0;j<10000;j++){
            lock.lock();
            try{
                i++;
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo t = new ReentrantLockDemo();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.start();t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
