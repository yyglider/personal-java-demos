package reflect.aop.dynamic_proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import reflect.aop.HelloImpl;

import java.lang.reflect.Method;

/**
 * Created by yaoyuan on 2017/3/31.
 * CGLib 给我们提供的是方法级别的代理，也可以理解为对方法的拦截
 */
public class CGlibHelloProxy implements MethodInterceptor {

    public <T> T getProxy(Class<T> cls){
        return (T) Enhancer.create(cls,this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before(); //
        Object result = methodProxy.invokeSuper(o, args);
        after(); //
        return result;
    }

    private void before(){
        System.out.println("before");
    }

    private void after(){
        System.out.println("after");
    }

    public static void main(String[] args) {
        CGlibHelloProxy cGlibHelloProxy = new CGlibHelloProxy();
        HelloImpl helloProxy = cGlibHelloProxy.getProxy(HelloImpl.class);
        helloProxy.say("jack");
    }
}
