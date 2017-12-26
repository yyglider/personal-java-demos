package design_parttern.observer;

/**
 * subject - observer
 * 实现了表示层和数据层的分离
 * 通过抽象的方式建立了低耦合
 * 支持广播通信
 * 实现了开闭原则
 */
public class Runner {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();
        weatherStation.addDisplay(new ForecastDisplay());

        weatherStation.run();
    }
}
