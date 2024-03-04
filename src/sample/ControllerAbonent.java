package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ControllerAbonent {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonSave;

    @FXML
    private ComboBox<String> combo_lgota;

    @FXML
    private ComboBox<String> combo_tarif;

    @FXML
    private TextField textBoxFather;

    @FXML
    private TextField textBoxLog;

    @FXML
    private TextField textBoxName;

    @FXML
    private TextField textBoxSurn;

    ObservableList<String> tarifs;
    ObservableList<String> lgoty;

    {
        try {
            tarifs = FXCollections.observableArrayList(DataBaseQuery.getTarifs());
            lgoty = FXCollections.observableArrayList(DataBaseQuery.getLgoty());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        combo_lgota.setItems(lgoty);
        combo_tarif.setItems(tarifs);
        if (StaticVariables.getWhatToDo() == 1) {
            textBoxSurn.setText(StaticVariables.getItems()[1]);
            textBoxName.setText(StaticVariables.getItems()[2]);
            textBoxFather.setText(StaticVariables.getItems()[3]);
            textBoxLog.setText(StaticVariables.getItems()[4]);
            combo_tarif.getSelectionModel().select(StaticVariables.getItems()[5]);
            combo_lgota.getSelectionModel().select(StaticVariables.getItems()[6]);
        }
        buttonClear.setOnAction((event -> {
            textBoxName.clear();
            textBoxSurn.clear();
            textBoxFather.clear();
            textBoxLog.clear();
            combo_tarif.getSelectionModel().clearSelection();
            combo_lgota.getSelectionModel().clearSelection();
        }));
        this.buttonSave.setOnAction(event -> {
            Integer lg;
            if (combo_lgota.getSelectionModel().getSelectedItem().equals("Нет льготы")) {
                lg = null;
            } else {
                lg = combo_lgota.getSelectionModel().getSelectedIndex() + 1;
            }
            if (textBoxName.getText() != null && textBoxSurn.getText() != null && textBoxFather.getText() != null && textBoxLog.getText() != null && combo_tarif.getSelectionModel() != null)
                if (StaticVariables.getWhatToDo() == 1) {
                    try {
                        DataBaseQuery.changeAbonent(StaticVariables.getId(), textBoxSurn.getText(), textBoxName.getText(), textBoxFather.getText(), textBoxLog.getText(), (combo_tarif.getSelectionModel().getSelectedIndex() + 1), lg);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("The subscriber was successfully changed. Click on 'Update' to see the changes");
                        alert.showAndWait();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    try {
                        DataBaseQuery.addAbonent(textBoxSurn.getText(), textBoxName.getText(), textBoxFather.getText(), textBoxLog.getText(), (combo_tarif.getSelectionModel().getSelectedIndex() + 1), lg);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("The subscriber was successfully changed. Click on 'Update' to see the changes");
                        alert.showAndWait();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
            this.buttonSave.getScene().getWindow().hide();
        });
    }
}