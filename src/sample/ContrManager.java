package sample;

import java.io.FileWriter;
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

public class ContrManager {

    private final ObservableList<String> requests = FXCollections.observableArrayList(
            "На какую сумму были произведены платежи за текущий год",
            "Сколько абонентов проконсультировал каждый консультант",
            "Сколько абонентов подключены к каждой льготе",
            "Для каждого тарифа определить вид звонка, которого совершено наибольшее количество раз",
            "Для каждого консультанта определить абонента, которого он консультировал наибольшее количество раз",
            "Каких типов платежей не было за последний год",
            "Список абонентов с комментариями «Больше всего раз обращался за консультациями» и «Никогда не обращался за консультациями»"
    );

    private final ObservableList<String> reports = FXCollections.observableArrayList(
            "Сколько абонентов проконсультировал каждый консультант",
            "Сколько абонентов подключены к каждой льготе",
            "Для каждого тарифа определить вид звонка, которого совершено наибольшее количество раз",
            "Для каждого консультанта определить абонента, которого он консультировал наибольшее количество раз"
    );

    @FXML
    private Button button_Apply;

    @FXML
    private Button button_Exit;

    @FXML
    private Button button_Print;

    @FXML
    private Button button_Reload;

    @FXML
    private ComboBox<String> combo_lgota;

    @FXML
    private ComboBox<String> combo_report;

    @FXML
    private ComboBox<String> combo_request;

    @FXML
    private ComboBox<String> combo_tarif;

    @FXML
    private TableView table_clients;

    @FXML
    private TableView table_request;

    @FXML
    private TextArea textArea_report;

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
        combo_request.setItems(requests);
        try {
            tarifs = FXCollections.observableArrayList(DataBaseQuery.getTarifs());
            lgoty = FXCollections.observableArrayList(DataBaseQuery.getLgoty());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        combo_tarif.setItems(tarifs);
        combo_lgota.setItems(lgoty);
        EventHandler<ActionEvent> clickLoadTable = actionEvent -> {
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getTableClients());
                String[] names = new String[8];
                names[0] = "Number";
                names[1] = "Surname";
                names[2] = "Name";
                names[3] = "Fathers name";
                names[4] = "Telephone";
                names[5] = "Sum";
                names[6] = "Tarif";
                names[7] = "Lgota";
                createTabCons(8, names, data, table_clients);
            } catch (ClassNotFoundException | SQLException throwables) {
                throwables.printStackTrace();
            }
        };
        button_Exit.setOnAction(event -> this.openNewScene("/sample/sample.fxml"));
        button_Reload.setOnAction(clickLoadTable);

        button_Apply.setOnAction(event -> {
            TableView.TableViewSelectionModel selectionModel = table_clients.getSelectionModel();
            int i = Integer.parseInt(getStringFromObservableList(selectionModel.getSelectedItems())[0]);
            if (!table_clients.getSelectionModel().isEmpty()) {
                try {
                    DataBaseQuery.editTarifLgota(i, combo_tarif.getValue(), combo_lgota.getValue());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Данные клиента обновлены! Перезагрузите таблицу для просмотра");
                    alert.showAndWait();
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Выберите клиента!");
                alert.showAndWait();
            }
        });

        combo_request.setOnAction(event -> {
            int num = combo_request.getSelectionModel().getSelectedIndex();
            num++;
            switch (num) {
                case (1):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getThisYearSum());
                        String[] names = new String[1];
                        names[0] = "Sum";
                        createTabCons(1, names, data, table_request);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (2):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getAdmConsultClient());
                        String[] names = new String[4];
                        names[0] = "Surname";
                        names[1] = "Name";
                        names[2] = "Fathername";
                        names[3] = "Amount";
                        createTabCons(4, names, data, table_request);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (3):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getExemptionClients());
                        String[] names = new String[2];
                        names[0] = "Lgota";
                        names[1] = "Abonens";
                        createTabCons(2, names, data, table_request);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (4):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getMaxTarifCall());
                        String[] names = new String[3];
                        names[0] = "Tarif";
                        names[1] = "CallType";
                        names[2] = "Amount";
                        createTabCons(3, names, data, table_request);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (5):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getMaxAdminClient());
                        String[] names = new String[8];
                        names[0] = "Surname_adm";
                        names[1] = "Name_adm";
                        names[2] = "Fathername_adm";
                        names[3] = "Amount";
                        names[4] = "Surname_ab";
                        names[5] = "Name_ab";
                        names[6] = "Fathername_ab";
                        names[7] = "Telephone";
                        createTabCons(8, names, data, table_request);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (6):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getThisYearNoTypPlat());
                        String[] names = new String[1];
                        names[0] = "CallType";
                        createTabCons(1, names, data, table_request);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (7):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getMaxMinConsult());
                        String[] names = new String[5];
                        names[0] = "Surname_ab";
                        names[1] = "Name_ab";
                        names[2] = "Fathername_ab";
                        names[3] = "Telephone";
                        names[4] = "Commentary";
                        createTabCons(5, names, data, table_request);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
            }
        });
        combo_report.setItems(reports);
        combo_report.setOnAction(event -> {
            int num = combo_report.getSelectionModel().getSelectedIndex();
            num++;
            switch (num) {
                case (1):
                    textArea_report.setText("\t\t\t\t\t\t\t\tОтчёт № 1  " +
                            "\n\n\n\t\t\tКоличество проведённых консультаций каждым консультантом\n\n");
                    try {
                        for (int i = 0; i < DataBaseQuery.reportAmountAdConsult().size(); i++) {
                            textArea_report.setText(textArea_report.getText() + "\n" + DataBaseQuery.reportAmountAdConsult().get(i) + "\n");
                        }
                        textArea_report.setText(textArea_report.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (2):
                    textArea_report.setText("\t\t\t\t\t\t\t\tОтчёт № 2  " +
                            "\n\n\t\t\tКоличество абонентов, подключённых к каждой льготе\n\n");
                    try {
                        for (int i = 0; i < DataBaseQuery.reportAmountAbLg().size(); i++) {
                            textArea_report.setText(textArea_report.getText() + "\n" + DataBaseQuery.reportAmountAbLg().get(i) + "\n");
                        }
                        textArea_report.setText(textArea_report.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (3):
                    textArea_report.setText("\t\t\t\t\t\t\t\tОтчёт № 3  " +
                            "\n\n\tВиды звонков для каждого тарифа, совершённые наибольшее количество раз \n\n");
                    try {
                        for (int i = 0; i < DataBaseQuery.reportMaxCallTarif().size(); i++) {
                            textArea_report.setText(textArea_report.getText() + "\n№" + (i + 1) + " " + DataBaseQuery.reportMaxCallTarif().get(i) + "\n");
                        }
                        textArea_report.setText(textArea_report.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (4):
                    textArea_report.setText("\t\t\t\t\t\t\t\tОтчёт № 4  " +
                            "\n\nДля каждого консультанта определить абонента, которого он консультировал наибольшее количество раз\n\n");
                    try {
                        for (int i = 0; i < DataBaseQuery.reportAdMaxConsultAb().size(); i++) {
                            textArea_report.setText(textArea_report.getText() + "\n№" + (i + 1) + " " + DataBaseQuery.reportAdMaxConsultAb().get(i) + "\n");
                        }
                        textArea_report.setText(textArea_report.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
            }
        });

        this.button_Print.setOnAction(event -> {
            try (FileWriter writer = new FileWriter("Manager_Report_No" + (combo_report.getSelectionModel().getSelectedIndex() + 1) + ".TXT", false)) {
                writer.write(textArea_report.getText());
                writer.flush();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Отчёт успешно сохранён!");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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

    public void createTabCons(int tables, String[] names, ObservableList<ObservableList> data, TableView tab) throws SQLException, ClassNotFoundException { //функция создания колонок таблицы и их заполнения
        tab.getColumns().clear();
        for (int i = 0; i < tables; i++) {
            final int indexColumn = i;
            TableColumn tableColumn = new TableColumn(names[i]);
            tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(indexColumn).toString()));
            tab.getColumns().add(tableColumn);
        }
        tab.setItems(data);
    }

    public String[] getStringFromObservableList(ObservableList selectedCells) {
        String[] arr = selectedCells.get(0).toString().split(", ");
        arr[0] = arr[0].substring(1);
        arr[arr.length - 1] = arr[arr.length - 1].substring(0, arr[arr.length - 1].length() - 1);
        for (String s : arr) {
            System.out.print(s + "\n");
        }
        for (Object selectedCell : selectedCells) {
            System.out.print(selectedCell);
        }
        return arr;
    }
}