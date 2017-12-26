package design_parttern.command;

/**
 * 命令模式是对命令的封装
 * 命令模式把发出命令的责任和执行命令的责任分割开，委派给不同的对象。
 * 命令允许请求的一方和接收请求的一方能够独立演化，从而具有以下的优点：
 *（1）命令模式使新的命令很容易地被加入到系统里。
 *（2）允许接收请求的一方决定是否要否决请求。
 *（3）能较容易地设计一个命令队列。
 *（4）可以容易地实现对请求的撤销和恢复。
 *（5）在需要的情况下，可以较容易地将命令记入日志。
 */
public class Runner {
    public static void main(String[] args) {
        //创建接收者
        Receiver receiver = new Receiver();
        //创建命令对象，设定它的接收者
        Command command = new ConcreteCommand(receiver);
        //创建请求者，把命令对象设置进去
        Invoker invoker = new Invoker(command);
        //执行方法
        invoker.action();
    }
}
