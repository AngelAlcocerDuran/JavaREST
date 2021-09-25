package mx.edu.utez.database;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
    }

    public static void main(String[] args) {
        try{
            Connection con = ConnectionDB.getConnection();
            System.out.println("Conexion exitosa!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
