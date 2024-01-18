package sn.groupeisi.devoir0124.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import sn.groupeisi.devoir0124.tools.Outils;

import java.io.IOException;

public class ElecteurController {

    @FXML
    private Button btn_back;

    @FXML
    private TableColumn<?, ?> full_nameC;

    @FXML
    private TableColumn<?, ?> idC;

    @FXML
    private TableView<?> listParrain;

    @FXML
    private TableView<?> listeCandidat;

    @FXML
    private TableColumn<?, ?> nameC;

    @FXML
    private CheckBox parrainage_state;

    @FXML
    private TableColumn<?, ?> roleC;

    @FXML
    private TextField tfd_chosen;

    @FXML
    private Text txt_etat;

    @FXML
    private Text txt_parrain;

    @FXML
    private TableColumn<?, ?> user_stateC;

    @FXML
    void chose_parrain_click(ActionEvent event) {

    }

    @FXML
    void getData(MouseEvent event) {

    }

    @FXML
    void login_page(ActionEvent event) throws IOException {
        Outils.load(event, "Login", "/pages/login.fxml");
    }

}
