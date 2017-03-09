package main.java.jvm.gc;

/**
 * Created by yaoyuan on 2017/3/1.
 * 模拟引用计数法的缺陷——循环引用——实际上，jvm并不是使用该方法判定gc标准的，所以本例中的对象是会被回收的。
 */
public class RererenceCountingGC {

    public Object instance = null;

    private static final int _1MB = 1024*1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        RererenceCountingGC testGC_A = new RererenceCountingGC();
        RererenceCountingGC testGC_B = new RererenceCountingGC();
        testGC_A.instance = testGC_B;
        testGC_B.instance = testGC_A;

        testGC_A = null;
        testGC_B = null;

        System.gc();
    }
}
