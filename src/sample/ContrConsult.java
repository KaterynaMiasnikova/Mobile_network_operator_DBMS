package sample;

import java.io.IOException;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ContrConsult {
    @FXML
    private Button button_AddConsult;

    @FXML
    private Button button_Exit;

    @FXML
    private Button button_Reload;

    @FXML
    private ComboBox<String> combo_Zapros;

    @FXML
    private TableView table_Consultations;

    @FXML
    private TableView table_Zapros;

    @FXML
    private TextField textArea_Duration;

    @FXML
    private TextArea textArea_Opys;

    @FXML
    private TextField textArea_Tel;

    @FXML
    private TextField textArea_Info;

    @FXML
    private Button button_Solve;

    private final ObservableList<String> requests = FXCollections.observableArrayList (
            "Find all subscribers with numbers starting with...",
            "Find all consultations whose description begins with...",
            "How many subscribers are connected to the tariff...",
            "Which subscriber made the most calls"
            "List of active tariffs",
            "List of active benefits"
    );

    @FXML
    void initialize() {

        EventHandler<ActionEvent> clickLoadTable = actionEvent -> {
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getTableConsult());
                String[] names = new String[6];
                names[0] = "Number";
                names[1] = "Date of consultation";
                names[2] = "Abonent";
                names[3] = "Consultor";
                names[4] = "Duration";
                names[5] = "Opysanie";
                createTabCons(6, names, data, table_Consultations);
            } catch (ClassNotFoundException | SQLException throwables) {
                throwables.printStackTrace();
            }
        };
        button_Reload.setOnAction(clickLoadTable);
        combo_Zapros.setItems(requests);
        combo_Zapros.setOnAction(event -> {
            int num = combo_Zapros.getSelectionModel().getSelectedIndex();
            num++;
            if (num == 1) {
                textArea_Info.setVisible(true);
                button_Solve.setVisible(true);
                this.button_Solve.setOnAction(event1 -> {
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getClientsWithTel(textArea_Info.getText()));
                        String[] names = new String[4];
                        names[0] = "Surname";
                        names[1] = "Name";
                        names[2] = "Fathername";
                        names[3] = "Telephone";
                        createTabCons(4, names, data, table_Zapros);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                });
            } // REQUEST #1
            else if (num == 2) {
                textArea_Info.setVisible(true);
                button_Solve.setVisible(true);
                this.button_Solve.setOnAction(event1 -> {
                try {
                    ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getQuestConsult(textArea_Info.getText()));
                    String[] names = new String[3];
                    names[0] = "Number";
                    names[1] = "Date of consultation";
                    names[2] = "Opysanie";
                    createTabCons(3, names, data, table_Zapros);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }});
            } // REQUEST #2
            else if (num == 3) {
                textArea_Info.setVisible(true);
                button_Solve.setVisible(true);
                this.button_Solve.setOnAction(event1 -> {
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getTarifAbonents(textArea_Info.getText()));
                        String[] names = new String[1];
                        names[0] = "Amount of abonents";
                        createTabCons(1, names, data, table_Zapros);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                });
            } // REQUEST #3
            else if (num == 4) {
                    textArea_Info.setVisible(false);
                    button_Solve.setVisible(false);
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getMaxCalls());
                        String[] names = new String[5];
                        names[0] = "Surname";
                        names[1] = "Name";
                        names[2] = "Fathername";
                        names[3] = "Telephone";
                        names[4] = "Amount of Calls";
                        createTabCons(5, names, data, table_Zapros);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }

            } // REQUEST #4
            else if (num == 5) {
                textArea_Info.setVisible(false);
                button_Solve.setVisible(false);
                try {
                    ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getAllTable("tarif"));
                    String[] names = new String[7];
                    names[0] = "Number";
                    names[1] = "Name";
                    names[2] = "Price";
                    names[3] = "Internet";
                    names[4] = "Time";
                    names[5] = "Time on other operators";
                    names[6] = "Comment";
                    createTabCons(7, names, data, table_Zapros);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
            else if (num == 6) {
                textArea_Info.setVisible(false);
                button_Solve.setVisible(false);
                try {
                    ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getAllTable("lgota"));
                    String[] names = new String[3];
                    names[0] = "Number";
                    names[1] = "Type of lgota";
                    names[2] = "Size of lgota";
                    createTabCons(3, names, data, table_Zapros);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        button_AddConsult.setOnAction(event ->{
            if(textArea_Tel.getText() != "" && textArea_Duration.getText() != "" && textArea_Opys.getText() != "" && textArea_Tel.getText() != null && textArea_Duration.getText() != null && textArea_Opys.getText() != null){
                try {
                    DataBaseQuery.addConsultation(textArea_Tel.getText(),textArea_Opys.getText(), textArea_Duration.getText());
                    textArea_Opys.clear();
                    textArea_Tel.clear();
                    textArea_Duration.clear();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Consultation added! Reload the consultation table");
                    alert.showAndWait();
                } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Fill in all the fields!");
                    alert.showAndWait();
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fill in all the fields!");
                alert.showAndWait();
            }
        });
        button_Exit.setOnAction(event -> this.openNewScene("/sample/sample.fxml"));
    }

    public void createTabCons(int tables, String [] names, ObservableList<ObservableList> data,TableView tab) throws SQLException, ClassNotFoundException { //функция создания колонок таблицы и их заполнения
        tab.getColumns().clear();
        for (int i = 0; i < tables; i++) {
            final int indexColumn = i;
            TableColumn tableColumn = new TableColumn(names[i]);
            tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(indexColumn).toString()));
            tab.getColumns().add(tableColumn);
        }
        tab.setItems(data);
    }

    public void openNewScene(String window) {
        this.button_Exit.getScene().getWindow().hide();
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
        stage.showAndWait();
    }

}
