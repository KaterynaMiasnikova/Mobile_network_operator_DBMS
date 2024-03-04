package sample;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    @FXML
    public TextField labelLogin;
    @FXML
    public TextField labelPassword;
    @FXML
    public Button buttonLog;

    @FXML
    void initialize() {
        this.buttonLog.setOnAction((event -> {
            CurrentUser ru = new CurrentUser();
            ru.setLoginCur(this.labelLogin.getText());
            ru.setPasswordCur(this.labelPassword.getText());
            try {
                if (DataBaseQuery.authorize(ru)) {
                    switch (ru.getJob()) {
                        case ("consultant") -> this.openNewScene("/sample/consultor.fxml");
                        case ("manager") -> this.openNewScene("/sample/manager.fxml");
                        case ("admin") -> this.openNewScene("/sample/admin.fxml");
                        default -> {}
                    }
                }
            } catch (SQLException | ClassNotFoundException var4) {
                var4.printStackTrace();
            }
        }));
    }

    public void openNewScene(String window) {
        this.buttonLog.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException var5) {
            var5.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}