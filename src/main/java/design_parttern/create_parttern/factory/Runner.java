package design_parttern.create_parttern.factory;


/**
 * Created by yaoyuan on 2017/12/28.
 */
public class Runner {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        Class<?> clz = Class.forName("design_parttern.create_parttern.factory.OracleConnectionFactory");
//        ConnectionFactory factory = (ConnectionFactory) clz.newInstance();
//        Connection connection = factory.create();
//        connection.connect();

        Connection conn = ConnectionFactoryManager.getConnection("oracle");
        conn.connect();

    }
}
