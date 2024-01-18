package sn.groupeisi.devoir0124.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import sn.groupeisi.devoir0124.db.DB;
import sn.groupeisi.devoir0124.entities.User;
import sn.groupeisi.devoir0124.tools.Notification;
import sn.groupeisi.devoir0124.tools.Outils;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adminController implements Initializable {

    @FXML
    private Button btn_add;
    @FXML
    private Button btn_reset;
    @FXML
    private Text admin_name;
    private int idUser;

    @FXML
    private TableColumn<User, String> full_nameC;
    @FXML
    private TableColumn<User, String> user_stateC;

    @FXML
    private Button generate;

    @FXML
    private TableColumn<User, Integer> idC;

    @FXML
    private TableView<User> listeUtilisateur;

    @FXML
    private TableColumn<User, String> loginC;

    @FXML
    private TableColumn<User, String> passwordC;

    @FXML
    private RadioButton rb1;

    @FXML
    private RadioButton rb2;

    @FXML
    private ToggleGroup role;

    @FXML
    private TableColumn<User, Integer> roleC;

    @FXML
    private TableColumn<User, String> sateC;

    @FXML
    private TextField tfd_full_name;

    @FXML
    private TextField tfd_login;

    @FXML
    private TextField tfd_password;

    @FXML
    private Button btn_update;

    @FXML
    private CheckBox user_state;
    private DB db = new DB();
    private static int Uid = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        admin_name.setText("Thomas junior");
        btn_update .setDisable(true);
        loadtable();
    }
    @FXML
    void generate_login(ActionEvent event) {
        String full_name = tfd_full_name.getText().trim();
        String password = tfd_password.getText().trim();
        if(!full_name.isEmpty() && !password.isEmpty()) {
            String hshName = String.valueOf(password.hashCode());
            String login = full_name + hshName.charAt(0)+ "" + hshName.charAt(1) + "" + "" + hshName.charAt(6)+"@app";
            login =login.replaceAll("\\s", "");
            login = login.toLowerCase();
            tfd_login.setText(login);
        } else
            Notification.NotifError("Champs manquants", "Tout les champs doivent etre remplie");
    }
    @FXML
    void add_click(ActionEvent event) {

        if (someFieldIsEmpty()) {
            Notification.NotifError("Champs manquant","Tout les champs sont necessaire pour ajouter un utilisateur");
        } else {
             String sql = "Insert into users values (default, ?,?,?,?,?)";
             try {
                 db.prepareStatement(sql);

                 clearField();

                 db.getPsmt().setString(1, tfd_full_name.getText());
                 db.getPsmt().setString(2, tfd_login.getText());
                 db.getPsmt().setString(3, tfd_password.getText());
                 db.getPsmt().setString(5, "active");
                 if (user_state.isSelected())
                     db.getPsmt().setString(5, "disable");
                 if (rb1.isSelected()) {
                     db.getPsmt().setInt(4, 2);
                 } else {
                     db.getPsmt().setInt(4, 3);
                 }

                 int ok = db.exeUpdate();
                 if (ok != 0) {
                     Notification.NotifSuccess("Ajout effectuer", "l'utilisateur a été ajouter");
                     restartField();
                     loadtable();
                 }else
                     Notification.NotifError("Ajout annulé", "Une erreur inatendue");

                 db.closeConnection();

             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }

        }
    }
    private boolean someFieldIsEmpty() {
        String full_name = tfd_full_name.getText().trim();
        String password = tfd_password.getText().trim();
        String login = tfd_login.getText().trim();
        if (!full_name.isEmpty() && !password.isEmpty() && !login.isEmpty()) {
            return false;
        }
        return true;
    }
    private void clearField () {
        tfd_full_name.setText(tfd_full_name.getText().trim());
        tfd_password.setText( tfd_password.getText().trim());
        tfd_login.setText( tfd_login.getText().trim());
    }
    private void restartField() {
        tfd_full_name.setText("");
        tfd_password.setText("");
        tfd_login.setText("");
        user_state.setSelected(false);
        btn_add.setDisable(false);
    }
    private ObservableList<User> getusers() {
        String sql = "select * from users order by login asc";
        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            db.prepareStatement(sql);
            ResultSet resultSet = db.exeSelect();
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setId(resultSet.getInt("id"));
                user.setFull_name(resultSet.getString("full_name"));
                user.setPassword(resultSet.getString("password"));
                user.setRole_id(resultSet.getInt("role_id"));
                user.setUser_state(resultSet.getString("user_state"));
                users.add(user);
            }
            db.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    private void loadtable() {

        listeUtilisateur.setItems(getusers());
        idC.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        full_nameC.setCellValueFactory(new PropertyValueFactory<User, String>("full_name"));
        loginC.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        roleC.setCellValueFactory(new PropertyValueFactory<User, Integer>("role_id"));
        passwordC.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        user_stateC.setCellValueFactory(new PropertyValueFactory<User, String>("user_state"));


    }
    @FXML
    void updateUser(ActionEvent event) {
        String sql = "Update users set full_name = ?, login = ?, password = ?, user_state = ? where id = ?";
        try {
            db.prepareStatement(sql);
            db.getPsmt().setString(1,tfd_full_name.getText());
            db.getPsmt().setString(2, tfd_login.getText());
            System.out.println(tfd_login.getText());
            db.getPsmt().setString(3, tfd_password.getText());
            if (user_state.isSelected())
                db.getPsmt().setString(4, "disable");
            else
                db.getPsmt().setString(4, "active");
            db.getPsmt().setInt(5, idUser);


            db.exeUpdate();
            db.closeConnection();
            btn_add.setDisable(false);
            btn_update.setDisable(true);
            loadtable();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    @FXML
    void resetField_click(ActionEvent event) {
        restartField();
        btn_update.setDisable(true);
    }

    @FXML
    void login_page(ActionEvent event) throws IOException {
        Outils.load(event, "Login", "/pages/login.fxml");
    }

    @FXML
    void getData(MouseEvent event) {
        btn_update.setDisable(false);
        User user = listeUtilisateur.getSelectionModel().getSelectedItem();
        tfd_full_name.setText(user.getFull_name());
        tfd_password.setText(user.getPassword());
        tfd_login.setText(user.getLogin());
        idUser = user.getId();
        String state = user.getUser_state();
        System.out.println(state);

        if (state.equals("active")) {
            user_state.setSelected(false);

        }else {
            user_state.setSelected(true);
        }

        if (user.getRole_id() == 2 )
            rb1.fire();
        else if (user.getRole_id() == 3)
            rb2.fire();

        btn_add.setDisable(true);




    }
}


