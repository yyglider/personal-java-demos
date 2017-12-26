package design_parttern.command;

/**
 * Created by yaoyuan on 2017/12/26.
 */
public class Invoker {

    private Command command = null;

    public Invoker(Command command){
        this.command = command;
    }

    public void action(){
        command.execute();
    }

}
