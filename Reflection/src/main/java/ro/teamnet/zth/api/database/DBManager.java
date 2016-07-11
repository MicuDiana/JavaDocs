package ro.teamnet.zth.api.database;


import java.sql.*;

/**
 * Created by user on 08.07.2016.
 */
public class DBManager {

    final static String CONNECTION_STRING = "jdbc:oracle:thin:@" + DBProperties.IP + ":" + DBProperties.PORT + ":xe";

    private DBManager() {
        throw new UnsupportedOperationException();
    }

    private static void registerDriver() {

        try {
            Class.forName(DBProperties.DRIVER_CLASS);
        }
        catch(ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
    }

    public static Connection getConnection() {

        registerDriver();
        try {
            return DriverManager.getConnection(CONNECTION_STRING, DBProperties.USER, DBProperties.PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int checkConnection(Connection connection) {

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT 1 from dual");
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
