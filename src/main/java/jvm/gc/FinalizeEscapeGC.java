package main.java.jvm.gc;

/**
 * Created by yaoyuan on 2017/3/1.
 * 1、对象可以在被gc时自我拯救
 * 2、自救机会只有一次，因为finalize()方法最多只会被系统自动调用一次
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();
        //第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();

        Thread.sleep(500);

        if(SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("dead");
        }

        //第二次，拯救失败
        SAVE_HOOK = null;
        System.gc();

        Thread.sleep(500);

        if(SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("dead");
        }

    }
}
