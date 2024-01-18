package sn.groupeisi.devoir0124;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sn.groupeisi.devoir0124.db.DB;
import sn.groupeisi.devoir0124.entities.User;
import sn.groupeisi.devoir0124.metier.IUser;
import sn.groupeisi.devoir0124.metier.UserImpl;

public class App extends Application {


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/pages/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Helloo world");
        stage.setScene(scene);
        stage.show();
    }
}
