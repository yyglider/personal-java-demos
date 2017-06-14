package reflect.aop.static_proxy;

import reflect.aop.Hello;
import reflect.aop.HelloImpl;

/**
 * Created by yaoyuan on 2017/3/31.
 * 通过静态代理实现我们的需求需要我们在每个方法中都添加相应的逻辑，这里只存在两个方法所以工作量还不算大
 * 接口中包含上百个方法呢？这时候使用静态代理就会编写许多冗余代码
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
