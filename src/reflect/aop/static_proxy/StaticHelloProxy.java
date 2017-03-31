package reflect.aop.static_proxy;

import reflect.aop.Hello;
import reflect.aop.HelloImpl;

/**
 * Created by yaoyuan on 2017/3/31.
 */
public class StaticHelloProxy implements Hello {

    private HelloImpl helloImpl;

    public StaticHelloProxy(){
        helloImpl = new HelloImpl();
    }

    @Override
    public void say(String name) {
        before();
        helloImpl.say(name);
        after();
    }

    private void before(){
        System.out.println("before");
    }

    private void after(){
        System.out.println("after");
    }
}
