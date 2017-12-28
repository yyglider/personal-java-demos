package design_parttern.create_parttern.factory;

/**
 * Created by yaoyuan on 2017/12/28.
 */
public class OracleConnectionFactory implements ConnectionFactory {

    @Override
    public Connection create(String dbName) {
        if("oracle".equals(dbName)){
            return new OracleConnection();
        }
        return null;
    }
}
