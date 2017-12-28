package design_parttern.create_parttern.simpleFactory;

/**
 * Created by yaoyuan on 2017/12/28.
 */
public class ConnectionFactory {
    public static Connection create(String db){
        if("DB2".equals(db)){
            return new DB2Connection();
        }
        if("oracle".equals(db)){
            return new OracleConnection();
        }
        throw new RuntimeException("unsupported db type!");
    }
}
