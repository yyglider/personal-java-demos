package design_parttern.action_parttern.responsibility_chain;

/**
 * Created by yaoyuan on 2017/3/31.
 */
public class MsgProcessor {
    private String msg;
    private FilterChain fc;

    public FilterChain getFc() {
        return fc;
    }
    public void setFc(FilterChain fc) {
        this.fc = fc;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void processor(){
        fc.doFilter(msg,fc);;
    }
}