package reflect.aop;

/**
 * Created by yaoyuan on 2017/3/31.
 */
public class HelloImpl implements Hello {
    @Override
    public void say(String name) {
        System.out.println("hello " + name);
    }
}
