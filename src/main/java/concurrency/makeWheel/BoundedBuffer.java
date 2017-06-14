package concurrency.makeWheel;

/**
 *
 * 使用条件队列实现有界缓存
 *
 * 条件队列：使得一组等待的线程能够通过某种方式来等待特定的条件变成真,条件队列中的元素是一个个正在等待相关条件的线程。
 * 正如每个java对象都可以作为一个锁，同样，每一个对象同样可以作为一个条件队列。对象的内置锁和其内部的条件队列是相互关联的，要调用对象中条件队列的
 * 任意方法，必须持有该对象的锁。
 * 相比于使用“休眠”的有界缓存，条件队列在cpu效率、上下文切换、响应性方面进行了优化．
 * Created by yyglider on 2017/6/1.
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer {

    protected BoundedBuffer(int capacity) {
        super(capacity);
    }

    /**
     * Object.wait会自动释放锁，并请求操作系统挂起当前线程，从而使其他线程能够获得这个锁并修改对象状态。
     *
     * 由由于现场在条件谓词不为真的情况下也可以反复醒来，因此必须在一个循环中使用wait，并在每次迭代中都测试条件谓词。
     *
     * notify和notifyAll：调用notify时，jvm会从这个条件队列上等待的多个线程中选择一个来唤醒（这就有可能造成真正满足谓词条件的线程未被唤醒），
     * notifyAll会幻想所有在这个条件队列上的等待的线程，大多数情况下都回选择notifyAll。
     *
     * */
    public synchronized void put(V v) throws InterruptedException {
        while (isFull()){
            wait();  //等待
        }

        // boolean wasEmpty = isEmpty();
        doPut(v);
        // 优化：仅当缓存从空变为非空时，或由满转为非满时，才释放一个线程。
        //if(wasEmpty){
        //  notifyAll();
        // }
        notifyAll(); //通知
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()){
            wait();
        }
        V v = (V) doTake();
        notifyAll();
        return v;
    }



}
