module sn.groupeisi.devoir0124 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires TrayNotification;

    requires org.controlsfx.controls;

    opens sn.groupeisi.devoir0124 to javafx.fxml;
    exports sn.groupeisi.devoir0124;

    exports sn.groupeisi.devoir0124.controllers;
    opens sn.groupeisi.devoir0124.controllers to javafx.fxml;

    exports sn.groupeisi.devoir0124.entities;
    opens sn.groupeisi.devoir0124.entities to javafx.fxml;
}