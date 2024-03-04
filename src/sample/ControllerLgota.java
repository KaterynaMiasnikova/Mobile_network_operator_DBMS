package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ControllerLgota {
    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonSave;

    @FXML
    private TextField textBoxLgotaName;

    @FXML
    private TextField textBoxPart;

    @FXML
    void initialize() {
        buttonClear.setOnAction((event -> {
            textBoxLgotaName.clear();
            textBoxPart.clear();
        }));
        this.buttonSave.setOnAction(event -> {
            if (textBoxLgotaName.getText() != null && textBoxPart.getText() != null)
                    try {
                        DataBaseQuery.addLgota(textBoxLgotaName.getText(), Double.parseDouble(textBoxPart.getText()));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("The benefit has been successfully added. Click on 'Update' to see the changes");
                        alert.showAndWait();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
            this.buttonSave.getScene().getWindow().hide();
        });
    }
}