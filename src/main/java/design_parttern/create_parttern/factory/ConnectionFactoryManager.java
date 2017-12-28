package design_parttern.create_parttern.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoyuan on 2017/12/28.
 */
public class ConnectionFactoryManager {
    private static List<ConnectionFactory> factories = new ArrayList<>();

    static {
        register(new OracleConnectionFactory());
        register(new DB2ConnectionFactory());
    }

    public static Connection getConnection(String dbName) {
        for (ConnectionFactory factory : factories) {
            Connection connection = factory.create(dbName);
            if (connection != null) {
                return connection;
            }
        }
        throw new RuntimeException("can not create connnection");
    }

    public static void register(ConnectionFactory factory) {
        factories.add(factory);
    }
}
