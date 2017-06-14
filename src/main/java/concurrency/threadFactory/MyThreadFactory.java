package concurrency.threadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义的线程工厂，将一个特定于线程池的名字传递到构造函数，
 * 从而可以在线程转储和错误日志信息中区分来自不同线程池的线程。
 */
public final class MyThreadFactory implements ThreadFactory {

    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new MyAppThread(runnable, poolName);
    }
}
