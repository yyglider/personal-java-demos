package design_parttern.observer;

/**
 * Created by yaoyuan on 2017/12/26.
 */
public class WeatherData {

    private double temperature;
    private double pressure;
    private double humidity;

    public WeatherData(double temperature, double pressure, double humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }
}
