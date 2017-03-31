package main.java.jvm.oom;

/**
 * Created by yaoyuan on 2017/3/1.
 * 模拟虚拟机栈和本地方法栈溢出
 * Vm Args：-Xss128K
 */
public class StackOF {

    private int stackLength = 1;

    public void stackLeak(){
        stackLength ++;
        stackLeak();
    }

    public static void main(String[] args) {
        StackOF stackOF = new StackOF();
        try{
            stackOF.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length : " + stackOF.stackLength);
            throw e;
        }
    }
}
