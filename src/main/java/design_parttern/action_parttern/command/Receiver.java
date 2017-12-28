package design_parttern.action_parttern.command;

/**
 * Created by yaoyuan on 2017/12/26.
 */
public class Receiver {

    /**
     * 真正执行命令相应的操作
     */
    public void action(){
        System.out.println("receiver in action !");
    }
}
