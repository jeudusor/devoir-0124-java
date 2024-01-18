package sn.groupeisi.devoir0124.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sn.groupeisi.devoir0124.entities.User;
import sn.groupeisi.devoir0124.metier.IUser;
import sn.groupeisi.devoir0124.metier.UserImpl;
import sn.groupeisi.devoir0124.tools.Notification;
import sn.groupeisi.devoir0124.tools.Outils;

public class LoginController {

    @FXML
    private Button btn_connection;

    @FXML
    private TextField tfd_login;
    private String login;

    @FXML
    private PasswordField tfd_password;
    private String password;

    private IUser loginState = new UserImpl();

    @FXML
    void connection_click(ActionEvent event) {
        login = tfd_login.getText();
        password = tfd_password.getText();
        System.out.println("hello");

        if (login.isEmpty() || password.isEmpty())
            Notification.NotifError("Saisi incorrect", "Tous les champs doivent etre remplis");
        else {
            try {
                User user = loginState.doConnexion(login, password);
                if (user != null) {
                    if (user.getRole_id() == 1) {
                        Outils.load(event, "ADMINISTRATION", "/pages/admin.fxml");
                    }
                    else if (user.getRole_id() == 2 || user.getRole_id() == 3) {
                        Outils.load(event, "Parrainage", "/pages/electeur.fxml");
                    }
                    Notification.NotifSuccess("Connexion réussie", "Vous etes connecter :" + user.getFull_name());
                } else
                    Notification.NotifError("Connexion echoue", "Aucun utilisateur correspondant trouvé !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
