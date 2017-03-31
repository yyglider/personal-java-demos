package main.java.jvm.oom;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoyuan on 2017/3/1.
 * 模拟运行时常来池导致的内存溢出异常（也是方法区的一部分）
 * 可以通过-XX：PermSize 和 -XX：MaxPermSize限制方法区大小
 * main.java.jvm args： -XX:PermSize=10M XX:MaxPermSize=10M
 * */

public class RuntimeConstantPoolOM {
    public static void main(String[] args) {
        //使用List保持常量池的引用，避免Full GC
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true){
            list.add(String.valueOf(i++).intern());//将String对象添加到常量池
        }
    }
}
