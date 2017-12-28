package design_parttern.action_parttern.observer;

/**
 * Created by yaoyuan on 2017/12/26.
 */
public class ForecastDisplay implements Display {

    @Override
    public void update(WeatherData data) {
        System.out.println("this is Forecast Display");

    }
}
