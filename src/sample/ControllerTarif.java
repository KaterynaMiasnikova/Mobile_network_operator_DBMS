package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ControllerTarif {
    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonSave;

    @FXML
    private TextField textBoxCallIn;

    @FXML
    private TextField textBoxCallOut;

    @FXML
    private TextField textBoxEnternet;

    @FXML
    private TextField textBoxName;

    @FXML
    private TextField textBoxPrice;

    @FXML
    void initialize() {
        if (StaticVariables.getWhatToDo() == 1) {
            textBoxName.setText(StaticVariables.getItems()[1]);
            textBoxPrice.setText(StaticVariables.getItems()[2]);
            textBoxEnternet.setText(StaticVariables.getItems()[3]);
            textBoxCallIn.setText(StaticVariables.getItems()[4]);
            textBoxCallOut.setText(StaticVariables.getItems()[5]);
        }
        buttonClear.setOnAction((event -> {
            textBoxName.clear();
            textBoxPrice.clear();
            textBoxEnternet.clear();
            textBoxCallIn.clear();
            textBoxCallOut.clear();
        }));
        this.buttonSave.setOnAction(event -> {
            if (textBoxName.getText() != null && textBoxPrice.getText() != null && textBoxEnternet.getText() != null && textBoxCallOut.getText() != null)
                if (StaticVariables.getWhatToDo() == 1) {
                    try {
                        DataBaseQuery.changeTarif(StaticVariables.getId(), textBoxName.getText(), Double.parseDouble(textBoxPrice.getText()), Integer.parseInt(textBoxEnternet.getText()), Integer.parseInt(textBoxCallIn.getText()), Integer.parseInt(textBoxCallOut.getText()));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("The tariff was successfully changed. Click on 'Update' to see the changes");
                        alert.showAndWait();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    try {
                        DataBaseQuery.addTarif(textBoxName.getText(), Double.parseDouble(textBoxPrice.getText()), Integer.parseInt(textBoxEnternet.getText()), Integer.parseInt(textBoxCallIn.getText()), Integer.parseInt(textBoxCallOut.getText()));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("The tariff has been successfully added. Click on 'Update' to see the changes");
                        alert.showAndWait();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
            this.buttonSave.getScene().getWindow().hide();
        });
    }
}