package concurrency.makeWheel;

/**
 * 通过轮询和休眠来实现简单的阻塞，调用者无需处理重试。
 *
 * 只要选择合适的休眠时间，就能在忙等待（自旋等待）导致的CPU时钟浪费和休眠等待导致的低响应之间寻找均衡。
 *
 * 如果存在某种挂起线程的方法，并且这种方法可以确保当某个条件成真时线程立即醒来，那么将极大地简化实现工作——条件队列。　
 *
 * Created by yyglider on 2017/6/1.
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer {
    private final int SLEEP_GRANLARITY = 1000;

    protected SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException{
        while (true){
            synchronized (this){
                if(!isFull()){
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(SLEEP_GRANLARITY);
        }
    }

    public V take(V v) throws InterruptedException{
        while (true){
            synchronized (this){
                if(!isEmpty()){
                    return (V) doTake();
                }
            }
            Thread.sleep(SLEEP_GRANLARITY);
        }
    }
}
