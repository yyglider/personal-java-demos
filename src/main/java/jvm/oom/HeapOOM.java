package main.java.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoyuan on 2017/3/1.
 * 模拟堆溢出
 * VM Args : -verbose:gc -Xms20M  -Xmx20M -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {
    static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    }
}
