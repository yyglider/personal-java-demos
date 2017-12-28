package design_parttern.action_parttern.observer;


import java.util.ArrayList;
import java.util.List;

/**
 * 被观察的subject,当发生变化时调用观察者的update方法
 * Created by yaoyuan on 2017/12/26.
 */
public class WeatherStation {
    List<Display> displays = new ArrayList<>();

    public void run(){
        WeatherData data = getLatestWeatherData();

        for (Display display : displays){
            display.update(data);
        }
    }

    /**
     * 添加观察者，如 weatherStation.addDisplay(new ForecastDisplay())
     * @param display
     */
    public void addDisplay(Display display){
        if(!this.displays.contains(display)){
            this.displays.add(display);
        }
    }

    public void removeDisplay(Display display){
        this.displays.remove(display);
    }

    public WeatherData getLatestWeatherData() {
        return new WeatherData(1.1,2.2,3.3);
    }
}
