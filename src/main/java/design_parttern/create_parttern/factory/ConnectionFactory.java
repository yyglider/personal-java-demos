package design_parttern.create_parttern.factory;

/**
 * Created by yaoyuan on 2017/12/28.
 */
public interface ConnectionFactory {
    Connection create(String dbName);
}
