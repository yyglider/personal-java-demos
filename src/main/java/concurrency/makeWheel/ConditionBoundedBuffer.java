package concurrency.makeWheel;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显示的Condition对象的有界缓存
 *
 * 内置条件队列存在一些缺陷，每个内置锁都只能有一个相关联的条件队列，然而，存在多个线程可能在同一个条件队列上等待不同的条件谓词的情况。
 * 如果想编写一个带有多个谓词条件的并发对象，或想过的除了条件队列外的更多控制权，就可以使用显示的lock,condition
 *
 * Created by yyglider on 2017/6/2.而不是内置的锁和条件队列。　
 */
public class ConditionBoundedBuffer<T> {
    private final int BUFFER_SIZE = 100;

    protected final Lock lock = new ReentrantLock();

    private final Condition  notFull = lock.newCondition(); //谓词条件：非满
    private final Condition notEmpty = lock.newCondition(); //谓词条件：非空

    private final T[] items = (T[]) new Object[BUFFER_SIZE];

    private int tail,head,count;

    //阻塞直到：not full
    public void put(T x) throws InterruptedException{
        lock.lock();
        try {
            while(count == items.length){
                notFull.await();
            }
            items[tail] = x;
            if(++tail == items.length){
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    //阻塞直到：not empty
    public T take() throws InterruptedException{
        lock.lock();
        try {
            while(count == 0){
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;

            if(++head == items.length){
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        }finally {
            lock.unlock();
        }
    }

}
