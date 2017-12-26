package concurrency.threadFactory;

/**
 * Created by yaoyuan on 2017/5/31.
 */
public interface ThreadFactory {
    Thread newThread(Runnable r, String poolName);
}
