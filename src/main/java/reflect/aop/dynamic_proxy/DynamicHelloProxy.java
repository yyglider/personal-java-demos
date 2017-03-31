package reflect.aop.dynamic_proxy;

import reflect.aop.Hello;
import reflect.aop.HelloImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by yaoyuan on 2017/3/31.
 * 只能代理一个有接口的类
 */
public class DynamicHelloProxy implements InvocationHandler {

    private Object target;

    public DynamicHelloProxy(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target,args);
        after();
        return result;
    }

    // Object 向下转型会引发IDE警告
    @SuppressWarnings("unchecked")
    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }

    private void before(){
        System.out.println("before");
    }

    private void after(){
        System.out.println("after");
    }

    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        DynamicHelloProxy dynamicHelloProxy = new DynamicHelloProxy(hello);

//        Hello helloProxy = (Hello) Proxy.newProxyInstance(
//                hello.getClass().getClassLoader(),
//                hello.getClass().getInterfaces(),
//                dynamicHelloProxy
//        );

        Hello helloProxy = dynamicHelloProxy.getProxy();
        helloProxy.say("jack");
    }
}
