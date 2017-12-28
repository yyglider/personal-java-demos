package design_parttern.action_parttern.responsibility_chain;

/**
 * Created by yaoyuan on 2017/3/31.
 */
public class Main {
    public static void main(String[] args) {
        String str = "你好！";
        MsgProcessor mp = new MsgProcessor();
        mp.setMsg(str);
        FilterChain fc = new FilterChain();
        fc.addFilter(new HTMLFilter())
                .addFilter(new TXTFilter());
        mp.setFc(fc);
        mp.processor();
    }
}
