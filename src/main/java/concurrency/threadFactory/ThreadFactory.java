package concurrency.threadFactory;

/**
 * Created by 006564 on 2017/5/31.
 */
public interface ThreadFactory {
    Thread newThread(Runnable r, String poolName);
}
