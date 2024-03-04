package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ControllerAdm {
    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonSave;

    @FXML
    private TextField textBoxAdr;

    @FXML
    private TextField textBoxFather;

    @FXML
    private TextField textBoxLog;

    @FXML
    private TextField textBoxName;

    @FXML
    private TextField textBoxPass;

    @FXML
    private TextField textBoxPost;

    @FXML
    private TextField textBoxSalary;

    @FXML
    private TextField textBoxSurn;

    @FXML
    void initialize() {
        if (StaticVariables.getWhatToDo() == 1) {
            textBoxName.setText(StaticVariables.getItems()[1]);
            textBoxSurn.setText(StaticVariables.getItems()[2]);
            textBoxFather.setText(StaticVariables.getItems()[3]);
            textBoxLog.setText(StaticVariables.getItems()[4]);
            textBoxPass.setText(StaticVariables.getItems()[5]);
            textBoxAdr.setText(StaticVariables.getItems()[6]);
            textBoxSalary.setText(StaticVariables.getItems()[7]);
            textBoxPost.setText(StaticVariables.getItems()[8]);
        }
        buttonClear.setOnAction((event -> {
            textBoxName.clear();
            textBoxSurn.clear();
            textBoxFather.clear();
            textBoxAdr.clear();
            textBoxSalary.clear();
            textBoxLog.clear();
            textBoxPass.clear();
            textBoxPost.clear();

        }));
        this.buttonSave.setOnAction(event -> {
            if (textBoxName.getText() != null && textBoxPost.getText() != null && textBoxPass.getText() != null && textBoxLog.getText() != null)
                if (StaticVariables.getWhatToDo() == 1) {
                    try {
                        DataBaseQuery.changeAdm(StaticVariables.getId(), textBoxName.getText(), textBoxSurn.getText(), textBoxFather.getText(), textBoxLog.getText(), textBoxPass.getText(), textBoxPost.getText(), textBoxAdr.getText(), textBoxSalary.getText());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("The employee was successfully changed. Click on 'Update' to see the changes");
                        alert.showAndWait();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    try {
                        DataBaseQuery.addAdm(textBoxName.getText(), textBoxSurn.getText(), textBoxFather.getText(), textBoxLog.getText(), textBoxPass.getText(), textBoxPost.getText(), textBoxAdr.getText(), textBoxSalary.getText());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("The employee was successfully changed. Click on 'Update' to see the changes");
                        alert.showAndWait();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
            this.buttonSave.getScene().getWindow().hide();
        });
    }
}