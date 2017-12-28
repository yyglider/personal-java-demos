package design_parttern.create_parttern.factory;

/**
 * Created by yaoyuan on 2017/12/28.
 */
public class OracleConnection implements Connection {
    @Override
    public void connect() {
        System.out.println("get oracle connection!");
    }
}
