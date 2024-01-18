package sn.groupeisi.devoir0124.metier;

import sn.groupeisi.devoir0124.db.DB;
import sn.groupeisi.devoir0124.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserImpl implements IUser{
    @Override
    public User doConnexion(String login, String password) {
        User user = null;
        ResultSet resultat;
        DB db = new DB();

        String sql = "select * from users where login = ? and password = ?";
        db.prepareStatement(sql);

        try {
            db.getPsmt().setString(1, login);
            db.getPsmt().setString(2, password);

            resultat = db.exeSelect();
            if (resultat.next()) {
                user = new User();
                user.setId(resultat.getInt("id"));
                user.setFull_name(resultat.getString("full_name"));
                user.setLogin(resultat.getString("login"));
                user.setRole_id(resultat.getInt("role_id"));
                user.setUser_state(resultat.getString("user_state"));
            }
            db.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return user;
    }
}
