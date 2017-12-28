package design_parttern.action_parttern.command;

/**
 * Created by yaoyuan on 2017/12/26.
 */
public class ConcreteCommand implements Command{

    private Receiver receiver = null;

    public ConcreteCommand(Receiver receiver){
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        //调接收者对象的相应方法，让接收者来真正执行功能
        receiver.action();
    }
}
