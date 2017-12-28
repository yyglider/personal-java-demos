package design_parttern.create_parttern.factory;

/**
 * Created by yaoyuan on 2017/12/28.
 */
public class DB2Connection implements Connection {
    @Override
    public void connect() {
        System.out.println("get db2 connection!");
    }
}
