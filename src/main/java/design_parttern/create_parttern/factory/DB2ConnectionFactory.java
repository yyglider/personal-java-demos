package design_parttern.create_parttern.factory;

/**
 * Created by yaoyuan on 2017/12/28.
 */
public class DB2ConnectionFactory implements ConnectionFactory {
    @Override
    public Connection create(String dbName) {
        if("db2".equals(dbName)){
            return new DB2Connection();
        }
        return null;
    }
}
