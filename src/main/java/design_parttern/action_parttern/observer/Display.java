package design_parttern.action_parttern.observer;

/**
 * Created by yaoyuan on 2017/12/26.
 */
public interface Display {
    /**
     * 观察者,当订阅的主题发生变化时，执行该函数。
     * 所有的观察类都必须实现该接口。
     */
    public void update(WeatherData data);
}
