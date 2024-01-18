package sn.groupeisi.devoir0124.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {

    private Connection connection;
    private PreparedStatement psmt;
    private ResultSet resultSet;
    private Integer ok;

    private void getConnection() {
        String host = "localhost";
        String database = "sponsorship_db";
        String user_name = "postgres";
        String password = "root";

        String url = "jdbc:postgresql://" + host + ":5432" + '/' + database;


        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user_name, password);
            System.out.println("connection réussi");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void prepareStatement(String sql) {
        try {
            getConnection();
            psmt = connection.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet  exeSelect() {
        resultSet = null;
        try {
            resultSet = psmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public int exeUpdate() {
        try {
            ok = psmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    public void closeConnection() {

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public PreparedStatement getPsmt() {
        return psmt;
    }
}
