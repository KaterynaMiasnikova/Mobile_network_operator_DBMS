package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Arrays;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ContrAdmin {
    private final ObservableList<String> requests = FXCollections.observableArrayList(
            "Каких клиентов консультировал администратор с заданной фамилией",
            "Какие типы платежей были произведены в этом месяце",
            "Найти всех абонентов с номером, по фрагменту начала",
            "Список клиентов, осуществивших платежи в заданный период",
            "Список консультантов, проводивших консультации в заданный период",
            "На какую сумму были произведены платежи за текущий год",
            "Платежи какого типа были сделаны больше всего раз",
            "Какие абоненты ни разу не обращались за консультациями",
            "Список тарифов с комментариями «Подключено наибольшее количество абонентов» и «Подключено наименьшее количество клиентов»"
    );

    private final ObservableList<String> reports = FXCollections.observableArrayList (
            "Сколько абонентов проконсультировал каждый консультант",
            "Сколько абонентов подключены к каждой льготе",
            "Для каждого тарифа определить вид звонка, которого совершено наибольшее количество раз",
            "Для каждого консультанта определить абонента, которого он консультировал наибольшее количество раз",
            "Список абонентов с комментариями «Больше всего раз обращался за консультациями» и «Никогда не обращался за консультациями»",
            "Список тарифов с комментариями «Подключено наибольшее количество абонентов» и «Подключено наименьшее количество клиентов»"
    );

    @FXML
    private AnchorPane anchorPane1;

    @FXML
    private TextArea boxResult;

    @FXML
    private Button buttonAb;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonCalls;

    @FXML
    private Button buttonChanbge;

    @FXML
    private Button buttonChangeUser;

    @FXML
    private Button buttonConsult;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonLgota;

    @FXML
    private Button buttonPayment;

    @FXML
    private Button buttonPrintReport;

    @FXML
    private Button buttonSolve;

    @FXML
    private Button buttonTarif;

    @FXML
    private Button buttonWork;

    @FXML
    private ComboBox<String> comboReports;

    @FXML
    private ComboBox<String> comboRequests;

    @FXML
    private TextField infBox;

    @FXML
    private Label labelAct;

    @FXML
    private Label labelHow;

    @FXML
    private Label labelRequest;

    @FXML
    private Label labelSolve;

    @FXML
    private Label labelTableName;

    @FXML
    private MenuItem menuItemAb;

    @FXML
    private MenuItem menuItemCalls;

    @FXML
    private MenuItem menuItemConsult;

    @FXML
    private MenuItem menuItemLgota;

    @FXML
    private MenuItem menuItemPay;

    @FXML
    private MenuItem menuItemRep1;

    @FXML
    private MenuItem menuItemRep2;

    @FXML
    private MenuItem menuItemRep3;

    @FXML
    private MenuItem menuItemRep4;

    @FXML
    private MenuItem menuItemRep5;

    @FXML
    private MenuItem menuItemRep6;

    @FXML
    private MenuItem menuItemRequest1;

    @FXML
    private MenuItem menuItemRequest2;

    @FXML
    private MenuItem menuItemRequest3;

    @FXML
    private MenuItem menuItemRequest4;

    @FXML
    private MenuItem menuItemRequest5;

    @FXML
    private MenuItem menuItemRequest6;

    @FXML
    private MenuItem menuItemRequest7;

    @FXML
    private MenuItem menuItemRequest8;

    @FXML
    private MenuItem menuItemRequest9;

    @FXML
    private MenuItem menuItemTarif;

    @FXML
    private MenuItem menuItemWork;

    @FXML
    private ImageView pic1;

    @FXML
    private ImageView pic2;

    @FXML
    private ImageView pic3;

    @FXML
    private ImageView pic4;

    @FXML
    private ImageView pic5;

    @FXML
    private ImageView picT1;

    @FXML
    private ImageView picT2;

    @FXML
    private SplitPane split;

    @FXML
    private TableView tableView;

    @FXML
    private TableView table_result;

    @FXML
    private Button buttonReset;


    @FXML
    void initialize() {
        this.buttonChangeUser.setOnAction(event -> this.openNewScene("/sample/sample.fxml"));
        EventHandler<ActionEvent> clickButtonAbonent = actionEvent -> {
            try {
                StaticVariables.setWindow("abonent");
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
                createContent_tab(8, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> clickButtonPayment = actionEvent -> {
            try {
                StaticVariables.setWindow("plat");
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getTablePlat());
                String[] names = new String[5];
                names[0] = "id_pl";
                names[1] = "dattim_pl";
                names[2] = "abonent_name";
                names[3] = "nazv_tp";
                names[4] = "siz_tp";
                createContent_tab(5, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> clickButtonTarif = actionEvent -> {
            try {
                StaticVariables.setWindow("tarif");
                createContent_tab("tarif");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> clickButtonCalls = actionEvent -> {
            try {
                StaticVariables.setWindow("zvonok");
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getTableCall());
                String[] names = new String[7];
                names[0] = "id_zv";
                names[1] = "dattim_zv";
                names[2] = "durat_zv";
                names[3] = "telfor_zv";
                names[4] = "abonent_name";
                names[5] = "nazv_vz";
                names[6] = "price_vz";
                createContent_tab(7, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> clickButtonWorker = actionEvent -> {
            try {
                StaticVariables.setWindow("adm");
                createContent_tab("adm");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> clickButtonConsults = actionEvent -> {
            try {
                StaticVariables.setWindow("consult");
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getTableConsult());
                String[] names = new String[6];
                names[0] = "Number";
                names[1] = "Date of consultation";
                names[2] = "Abonent";
                names[3] = "Consultor";
                names[4] = "Duration";
                names[5] = "Opysanie";
                createContent_tab(6, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> clickButtonLgota = actionEvent -> {
            try {
                StaticVariables.setWindow("lgota");
                createContent_tab("lgota");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };

        this.buttonAb.setOnAction(clickButtonAbonent);
        this.buttonPayment.setOnAction(clickButtonPayment);
        this.buttonCalls.setOnAction(clickButtonCalls);
        this.buttonLgota.setOnAction(clickButtonLgota);
        this.buttonWork.setOnAction(clickButtonWorker);
        this.buttonConsult.setOnAction(clickButtonConsults);
        this.buttonTarif.setOnAction(clickButtonTarif);

        this.menuItemAb.setOnAction(clickButtonAbonent);
        this.menuItemCalls.setOnAction(clickButtonCalls);
        this.menuItemLgota.setOnAction(clickButtonLgota);
        this.menuItemConsult.setOnAction(clickButtonConsults);
        this.menuItemWork.setOnAction(clickButtonWorker);
        this.menuItemTarif.setOnAction(clickButtonTarif);
        this.menuItemPay.setOnAction(clickButtonPayment);

        EventHandler<ActionEvent> request1 = actionEvent -> {
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getConsultClients(infBox.getText()));
                String[] names = new String[4];
                names[0] = "Surname";
                names[1] = "Name";
                names[2] = "Fathername";
                names[3] = "Telephone";
                createTab(4, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> request2 = actionEvent -> {
            this.labelRequest.setText(menuItemRequest2.getText());
            this.boxResult.setVisible(false);
            this.table_result.setVisible(true);
            split.setDividerPosition(0, 0);
            labelSolve.setVisible(false);
            infBox.setVisible(false);
            buttonSolve.setVisible(false);
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getThisMonthPay());
                String[] names = new String[1];
                names[0] = "Type of payment";
                createTab(1, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> request3 = actionEvent -> {
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getClientsWithTel(infBox.getText()));
                String[] names = new String[4];
                names[0] = "Surname";
                names[1] = "Name";
                names[2] = "Fathername";
                names[3] = "Telephone";
                createTab(4, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> request4 = actionEvent -> {
            String delimeter = " ";
            String[] tempStr;
            tempStr = infBox.getText().split(delimeter);
            for (String s : tempStr) {
                System.out.println(s + "\n");
            }
            Date from = Date.valueOf(tempStr[0]);
            Date to = Date.valueOf(tempStr[1]);
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getPeriodPay(from, to));
                String[] names = new String[5];
                names[0] = "Surname";
                names[1] = "Name";
                names[2] = "Fathername";
                names[3] = "Telephone";
                names[4] = "Date of payment";
                createTab(5, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> request5 = actionEvent -> {
            String delimeter = " ";
            String[] tempStr;
            tempStr = infBox.getText().split(delimeter);
            for (String s : tempStr) {
                System.out.println(s + "\n");
            }
            Date from = Date.valueOf(tempStr[0]);
            Date to = Date.valueOf(tempStr[1]);
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getPeriodConsult(from, to));
                String[] names = new String[5];
                names[0] = "Surname";
                names[1] = "Name";
                names[2] = "Fathers name";
                names[3] = "Date of Consult";
                names[4] = "Telephone of abonent";
                createTab(5, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> request6 = actionEvent -> {
            this.labelRequest.setText(menuItemRequest6.getText());
            this.boxResult.setVisible(false);
            this.table_result.setVisible(true);
            split.setDividerPosition(0, 0);
            labelSolve.setVisible(false);
            infBox.setVisible(false);
            buttonSolve.setVisible(false);
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getThisYearSum());
                String[] names = new String[1];
                names[0] = "Total summary of payments this year";
                createTab(1, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> request7 = actionEvent -> {
            this.labelRequest.setText(menuItemRequest7.getText());
            this.boxResult.setVisible(false);
            this.table_result.setVisible(true);
            split.setDividerPosition(0, 0);
            labelSolve.setVisible(false);
            infBox.setVisible(false);
            buttonSolve.setVisible(false);
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getMaxTypPlat());
                String[] names = new String[2];
                names[0] = "Type of payment";
                names[1] = "Summary of payments";
                createTab(2, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> request8 = actionEvent -> {
            this.labelRequest.setText(menuItemRequest8.getText());
            this.boxResult.setVisible(false);
            this.table_result.setVisible(true);
            split.setDividerPosition(0, 0);
            labelSolve.setVisible(false);
            infBox.setVisible(false);
            buttonSolve.setVisible(false);
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getNeverConsult());
                String[] names = new String[4];
                names[0] = "Surname";
                names[1] = "Name";
                names[2] = "Fathername";
                names[3] = "Telephone";
                createTab(4, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> request9 = actionEvent -> {
            this.labelRequest.setText(menuItemRequest9.getText());
            this.boxResult.setVisible(false);
            this.table_result.setVisible(true);
            split.setDividerPosition(0, 0);
            labelSolve.setVisible(false);
            infBox.setVisible(false);
            buttonSolve.setVisible(false);
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getMaxMinTarif());
                String[] names = new String[2];
                names[0] = "Tarif name";
                names[1] = "Comment";
                createTab(2, names, data);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };

        this.menuItemRequest1.setOnAction(event -> {
            this.labelRequest.setText(menuItemRequest1.getText());
            this.boxResult.setVisible(false);
            this.table_result.setVisible(true);
            split.setDividerPosition(0, 0);
            infBox.setVisible(true);
            buttonSolve.setVisible(true);
            labelSolve.setVisible(true);
            this.buttonSolve.setOnAction(request1);
        });
        this.menuItemRequest2.setOnAction(request2);
        this.menuItemRequest3.setOnAction(event -> {
            this.labelRequest.setText(menuItemRequest3.getText());
            this.boxResult.setVisible(false);
            this.table_result.setVisible(true);
            split.setDividerPosition(0, 0);
            infBox.setVisible(true);
            buttonSolve.setVisible(true);
            labelSolve.setVisible(true);
            this.buttonSolve.setOnAction(request3);
        });
        this.menuItemRequest4.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Инструкция");
            alert.setHeaderText(null);
            alert.setContentText("Напишите данные в следующем  виде:\n 'YEAR-MONTH-DAY YEAR-MONTH-DAY'");
            alert.showAndWait();
            this.labelRequest.setText(menuItemRequest4.getText());
            this.boxResult.setVisible(false);
            this.table_result.setVisible(true);
            split.setDividerPosition(0, 0);
            infBox.setVisible(true);
            buttonSolve.setVisible(true);
            labelSolve.setVisible(true);
            this.buttonSolve.setOnAction(request4);
        });
        this.menuItemRequest5.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Instructions");
            alert.setHeaderText(null);
            alert.setContentText("Write data in information box in such mask:\n 'YEAR-MONTH-DAY YEAR-MONTH-DAY'");
            alert.showAndWait();
            this.labelRequest.setText(menuItemRequest5.getText());
            this.boxResult.setVisible(false);
            this.table_result.setVisible(true);
            split.setDividerPosition(0, 0);
            infBox.setVisible(true);
            buttonSolve.setVisible(true);
            labelSolve.setVisible(true);
            this.buttonSolve.setOnAction(request5);
        });
        this.menuItemRequest6.setOnAction(request6);
        this.menuItemRequest7.setOnAction(request7);
        this.menuItemRequest8.setOnAction(request8);
        this.menuItemRequest9.setOnAction(request9);

        this.comboRequests.setItems(this.requests);
        comboRequests.setOnAction(event -> {
            buttonPrintReport.setVisible(false);
            int num = comboRequests.getSelectionModel().getSelectedIndex();
            num++;
            this.labelRequest.setText(comboRequests.getSelectionModel().getSelectedItem());
            this.boxResult.setVisible(false);
            this.table_result.setVisible(true);
            split.setDividerPosition(0, 0);
            if (num == 1 || num == 3 || num == 4 || num == 5) {
                infBox.setVisible(true);
                buttonSolve.setVisible(true);
                labelSolve.setVisible(true);
            } else {
                labelSolve.setVisible(false);
                infBox.setVisible(false);
                buttonSolve.setVisible(false);
            }
            switch (num) {
                case(1):
                    this.buttonSolve.setOnAction(request1);
                    break;
                case(2):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getThisMonthPay());
                        String[] names = new String[1];
                        names[0] = "Type of payment";
                        createTab(1, names, data);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case(3):
                    this.buttonSolve.setOnAction(request3);
                    break;
                case(4):
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Инструкция");
                    alert.setHeaderText(null);
                    alert.setContentText("Напишите данные в следующем  виде:\n 'YEAR-MONTH-DAY YEAR-MONTH-DAY'");
                    alert.showAndWait();
                    this.buttonSolve.setOnAction(request4);
                    break;
                case(5):
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Инструкция");
                    alert.setHeaderText(null);
                    alert.setContentText("Напишите данные в следующем  виде:\n 'YEAR-MONTH-DAY YEAR-MONTH-DAY'");
                    alert.showAndWait();
                    this.buttonSolve.setOnAction(request5);
                    break;
                case(6):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getThisYearSum());
                        String[] names = new String[1];
                        names[0] = "Total summary of payments this year";
                        createTab(1, names, data);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case(7):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getMaxTypPlat());
                        String[] names = new String[2];
                        names[0] = "Type of payment";
                        names[1] = "Summary of payments";
                        createTab(2, names, data);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case(8):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getNeverConsult());
                        String[] names = new String[4];
                        names[0] = "Surname";
                        names[1] = "Name";
                        names[2] = "Fathername";
                        names[3] = "Telephone";
                        createTab(4, names, data);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case(9):
                    try {
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getMaxMinTarif());
                        String[] names = new String[2];
                        names[0] = "Tarif name";
                        names[1] = "Comment";
                        createTab(2, names, data);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
            }
        });
        EventHandler<ActionEvent> report1 = actionEvent -> {
            split.setDividerPosition(0, 0);
            this.labelRequest.setText(comboReports.getSelectionModel().getSelectedItem());
            this.boxResult.setVisible(true);
            this.table_result.setVisible(false);
            this.buttonPrintReport.setVisible(true);
            boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 1  " +
                    "\n\n\n\t\t\tКоличество проведённых консультаций каждым консультантом\n\n");
            try {
                for (int i = 0; i < DataBaseQuery.reportAmountAdConsult().size(); i++) {
                    boxResult.setText(boxResult.getText() + "\n" + DataBaseQuery.reportAmountAdConsult().get(i) + "\n");
                }
                boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> report2 = actionEvent -> {
            split.setDividerPosition(0, 0);
            this.labelRequest.setText(comboReports.getSelectionModel().getSelectedItem());
            this.boxResult.setVisible(true);
            this.table_result.setVisible(false);
            this.buttonPrintReport.setVisible(true);
            boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 2  " +
                    "\n\n\t\t\tКоличество абонентов, подключённых к каждой льготе\n\n");
            try {
                for (int i = 0; i < DataBaseQuery.reportAmountAbLg().size(); i++) {
                    boxResult.setText(boxResult.getText() + "\n" + DataBaseQuery.reportAmountAbLg().get(i) + "\n");
                }
                boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> report3 = actionEvent -> {
            split.setDividerPosition(0, 0);
            this.labelRequest.setText(comboReports.getSelectionModel().getSelectedItem());
            this.boxResult.setVisible(true);
            this.table_result.setVisible(false);
            this.buttonPrintReport.setVisible(true);
            boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 3  " +
                    "\n\n\tВиды звонков для каждого тарифа, совершённые наибольшее количество раз \n\n");
            try {
                for (int i = 0; i < DataBaseQuery.reportMaxCallTarif().size(); i++) {
                    boxResult.setText(boxResult.getText() + "\n№" + (i + 1) + " " + DataBaseQuery.reportMaxCallTarif().get(i) + "\n");
                }
                boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> report4 = actionEvent -> {
            split.setDividerPosition(0, 0);
            this.labelRequest.setText(comboReports.getSelectionModel().getSelectedItem());
            this.boxResult.setVisible(true);
            this.table_result.setVisible(false);
            this.buttonPrintReport.setVisible(true);
            boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 4  " +
                    "\n\nДля каждого консультанта определить абонента, которого он консультировал наибольшее количество раз\n\n");
            try {
                for (int i = 0; i < DataBaseQuery.reportAdMaxConsultAb().size(); i++) {
                    boxResult.setText(boxResult.getText() + "\n№" + (i + 1) + " " + DataBaseQuery.reportAdMaxConsultAb().get(i) + "\n");
                }
                boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> report5 = actionEvent -> {
            split.setDividerPosition(0, 0);
            this.labelRequest.setText(comboReports.getSelectionModel().getSelectedItem());
            this.boxResult.setVisible(true);
            this.table_result.setVisible(false);
            this.buttonPrintReport.setVisible(true);
            boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 5  " +
                    "\n\nАбоненты, обращавшиеся за консультациями наибольшее количество раз и не обращавшиеся ни разу\n\n");
            try {
                for (int i = 0; i < DataBaseQuery.reportMaxNoConsult().size(); i++) {
                    boxResult.setText(boxResult.getText() + "\n№" + (i + 1) + " " + DataBaseQuery.reportMaxNoConsult().get(i) + "\n");
                }
                boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        EventHandler<ActionEvent> report6 = actionEvent -> {
            split.setDividerPosition(0, 0);
            this.labelRequest.setText(comboReports.getSelectionModel().getSelectedItem());
            this.boxResult.setVisible(true);
            this.table_result.setVisible(false);
            this.buttonPrintReport.setVisible(true);
            boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 6  " +
                    "\n\nТарифы, к которым подключено наибольшее и наименьшее количество абонентов\n\n");
            try {
                for (int i = 0; i < DataBaseQuery.reportMaxMinTarif().size(); i++) {
                    boxResult.setText(boxResult.getText() + "\n№" + (i + 1) + " " + DataBaseQuery.reportMaxMinTarif().get(i) + "\n");
                }
                boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        this.menuItemRep1.setOnAction(report1);
        this.menuItemRep2.setOnAction(report2);
        this.menuItemRep3.setOnAction(report3);
        this.menuItemRep4.setOnAction(report4);
        this.menuItemRep5.setOnAction(report5);
        this.menuItemRep6.setOnAction(report6);
        this.comboReports.setItems(this.reports);
        comboReports.setOnAction(event -> {
            split.setDividerPosition(0, 0);
            this.labelRequest.setText(comboReports.getSelectionModel().getSelectedItem());
            this.boxResult.setVisible(true);
            this.table_result.setVisible(false);
            this.buttonPrintReport.setVisible(true);
            int num = comboReports.getSelectionModel().getSelectedIndex();
            num++;
            switch (num) {
                case (1):
                    boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 1  " +
                            "\n\n\n\t\t\tКоличество проведённых консультаций каждым консультантом\n\n");
                    try {
                        for (int i = 0; i < DataBaseQuery.reportAmountAdConsult().size(); i++) {
                            boxResult.setText(boxResult.getText() + "\n" + DataBaseQuery.reportAmountAdConsult().get(i) + "\n");
                        }
                        boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (2):
                    boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 2  " +
                            "\n\n\t\t\tКоличество абонентов, подключённых к каждой льготе\n\n");
                    try {
                        for (int i = 0; i < DataBaseQuery.reportAmountAbLg().size(); i++) {
                            boxResult.setText(boxResult.getText() + "\n" + DataBaseQuery.reportAmountAbLg().get(i) + "\n");
                        }
                        boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (3):
                    boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 3  " +
                            "\n\n\tВиды звонков для каждого тарифа, совершённые наибольшее количество раз \n\n");
                    try {
                        for (int i = 0; i < DataBaseQuery.reportMaxCallTarif().size(); i++) {
                            boxResult.setText(boxResult.getText() + "\n№" + (i + 1) + " " + DataBaseQuery.reportMaxCallTarif().get(i) + "\n");
                        }
                        boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (4):
                    boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 4  " +
                            "\n\nДля каждого консультанта определить абонента, которого он консультировал наибольшее количество раз\n\n");
                    try {
                        for (int i = 0; i < DataBaseQuery.reportAdMaxConsultAb().size(); i++) {
                            boxResult.setText(boxResult.getText() + "\n№" + (i + 1) + " " + DataBaseQuery.reportAdMaxConsultAb().get(i) + "\n");
                        }
                        boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (5):
                    boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 5  " +
                            "\n\nАбоненты, обращавшиеся за консультациями наибольшее количество раз и не обращавшиеся ни разу\n\n");
                    try {
                        for (int i = 0; i < DataBaseQuery.reportMaxNoConsult().size(); i++) {
                            boxResult.setText(boxResult.getText() + "\n№" + (i + 1) + " " + DataBaseQuery.reportMaxNoConsult().get(i) + "\n");
                        }
                        boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case (6):
                    boxResult.setText("\t\t\t\t\t\t\t\tОтчёт № 6  " +
                            "\n\nТарифы, к которым подключено наибольшее и наименьшее количество абонентов\n\n");
                    try {
                        for (int i = 0; i < DataBaseQuery.reportMaxMinTarif().size(); i++) {
                            boxResult.setText(boxResult.getText() + "\n№" + (i + 1) + " " + DataBaseQuery.reportMaxMinTarif().get(i) + "\n");
                        }
                        boxResult.setText(boxResult.getText() + "\n\n\t" + CurrentUser.getSurname() + " " + CurrentUser.getName() + " " + CurrentUser.getFathername() + "\t\t\t\t\t\t\t\t\t\t" + java.time.LocalDate.now() + " ");
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
            }
        });
        this.buttonPrintReport.setOnAction(event -> {
            try (FileWriter writer = new FileWriter("Admin_Report_No" + (comboReports.getSelectionModel().getSelectedIndex() + 1) + ".TXT", false)) {
                writer.write(boxResult.getText());
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

        EventHandler<ActionEvent> clickButtonReset = actionEvent -> {
            try {
                switch (StaticVariables.getWindow()) {
                    case ("abonent") -> {
                        StaticVariables.setWindow("abonent");
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
                        createContent_tab(8, names, data);
                    }
                    case ("consult") -> {
                        StaticVariables.setWindow("consult");
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getTableConsult());
                        String[] names = new String[6];
                        names[0] = "Number";
                        names[1] = "Date of consultation";
                        names[2] = "Abonent";
                        names[3] = "Consultor";
                        names[4] = "Duration";
                        names[5] = "Opysanie";
                        createContent_tab(6, names, data);
                    }
                    case ("plat") -> {
                        StaticVariables.setWindow("plat");
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getTablePlat());
                        String[] names = new String[5];
                        names[0] = "id_pl";
                        names[1] = "dattim_pl";
                        names[2] = "abonent_name";
                        names[3] = "nazv_tp";
                        names[4] = "siz_tp";
                        createContent_tab(5, names, data);
                    }
                    case ("zvonok") -> {
                        StaticVariables.setWindow("zvonok");
                        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getTableCall());
                        String[] names = new String[7];
                        names[0] = "id_zv";
                        names[1] = "dattim_zv";
                        names[2] = "durat_zv";
                        names[3] = "telfor_zv";
                        names[4] = "abonent_name";
                        names[5] = "nazv_vz";
                        names[6] = "price_vz";
                        createContent_tab(7, names, data);
                    }
                    default -> createContent_tab(StaticVariables.getWindow());
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        };
        this.buttonReset.setOnAction(clickButtonReset);
        this.buttonAdd.setOnAction(event -> {
            StaticVariables.setWhatToDo(0);
            openOverlay("/sample/" + StaticVariables.getWindow() + ".fxml");
            System.out.println(tableView.getSelectionModel().getSelectedIndex());
        });
        this.buttonChanbge.setOnAction(event -> {
            TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
            StaticVariables.setWhatToDo(1);
            StaticVariables.setItems(getStringFromObservableList(selectionModel.getSelectedItems()));
            openOverlay("/sample/" + StaticVariables.getWindow() + ".fxml");
            System.out.println(Arrays.toString(StaticVariables.getItems()));
        });
        this.buttonDelete.setOnAction(event -> {
            TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
            String[] selectedCells = getStringFromObservableList(selectionModel.getSelectedItems());
            int id = Integer.parseInt(selectedCells[0]);
            try {
                DataBaseQuery.deleteRowFromTable(id);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void openOverlay(String overlay) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(overlay));
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

    public void openNewScene(String window) {
        this.buttonChangeUser.getScene().getWindow().hide();
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

    public void createContent_tab(String tableName) throws SQLException, ClassNotFoundException { //функция создания колонок таблицы и их заполнения
        split.setDividerPosition(0, 1);
        pic1.setVisible(false);
        pic2.setVisible(false);
        pic3.setVisible(false);
        pic4.setVisible(false);
        pic5.setVisible(false);
        picT1.setVisible(true);
        picT2.setVisible(true);
        tableView.setVisible(true);
        buttonReset.setVisible(true);
        switch (StaticVariables.getWindow()) {
            case ("adm"), ("abonent") -> {
                buttonAdd.setVisible(true);
                buttonDelete.setVisible(true);
                buttonChanbge.setVisible(true);
                labelAct.setVisible(true);
                labelHow.setVisible(true);
            }
            case ("tarif") -> {
                buttonAdd.setVisible(true);
                labelAct.setVisible(true);
                labelHow.setVisible(true);
                buttonDelete.setVisible(false);
                buttonChanbge.setVisible(true);
            }
            case ("lgota") -> {
                buttonAdd.setVisible(true);
                labelAct.setVisible(true);
                labelHow.setVisible(true);
                buttonDelete.setVisible(false);
                buttonChanbge.setVisible(false);
            }
            default -> {
                buttonAdd.setVisible(false);
                labelAct.setVisible(false);
                labelHow.setVisible(false);
                buttonDelete.setVisible(false);
                buttonChanbge.setVisible(false);
            }
        }
        labelTableName.setVisible(true);
        labelTableName.setText(tableName);
        tableView.getColumns().clear();
        buttonPrintReport.setVisible(false);
        for (int i = 0; i < Integer.parseInt(DataBaseQuery.columnsNumName(tableName)[0]); i++) {
            final int indexColumn = i;
            TableColumn tableColumn = new TableColumn(DataBaseQuery.columnsNumName(tableName)[i + 1]);
            tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(indexColumn).toString()));
            tableView.getColumns().add(tableColumn);
            System.out.println("Column [" + i + "] ");
        }
        ObservableList<ObservableList> data = FXCollections.observableArrayList(DataBaseQuery.getAllTable(tableName));
        tableView.setItems(data);
    }

    public void createContent_tab(int tables, String[] names, ObservableList<ObservableList> data) throws SQLException, ClassNotFoundException {
        split.setDividerPosition(0, 1);
        pic1.setVisible(false);
        pic2.setVisible(false);
        pic3.setVisible(false);
        pic4.setVisible(false);
        pic5.setVisible(false);
        picT1.setVisible(true);
        picT2.setVisible(true);
        tableView.setVisible(true);
        buttonReset.setVisible(true);
        buttonPrintReport.setVisible(false);
        switch (StaticVariables.getWindow()) {
            case ("adm"), ("abonent") -> {
                buttonAdd.setVisible(true);
                buttonDelete.setVisible(true);
                buttonChanbge.setVisible(true);
                labelAct.setVisible(true);
                labelHow.setVisible(true);
            }
            case ("lgota") -> {
                buttonChanbge.setVisible(false);
                buttonAdd.setVisible(true);
                labelAct.setVisible(true);
                labelHow.setVisible(true);
                buttonDelete.setVisible(false);
            }
            case ("tarif") -> {
                buttonAdd.setVisible(true);
                labelAct.setVisible(true);
                labelHow.setVisible(true);
                buttonDelete.setVisible(false);
                buttonChanbge.setVisible(true);
            }
            default -> {
                buttonAdd.setVisible(false);
                labelAct.setVisible(false);
                labelHow.setVisible(false);
                buttonDelete.setVisible(false);
                buttonChanbge.setVisible(false);
            }
        }
        labelTableName.setVisible(true);
        labelTableName.setText(StaticVariables.getWindow());
        tableView.getColumns().clear();
        for (int i = 0; i < tables; i++) {
            final int indexColumn = i;
            TableColumn tableColumn = new TableColumn(names[i]);
            tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(indexColumn).toString()));
            tableView.getColumns().add(tableColumn);
            System.out.println("Column [" + i + "] ");
        }
        tableView.setItems(data);
    }

    public void createTab(int tables, String[] names, ObservableList<ObservableList> data) throws SQLException, ClassNotFoundException { //функция создания колонок таблицы и их заполнения
        table_result.getColumns().clear();
        for (int i = 0; i < tables; i++) {
            final int indexColumn = i;
            TableColumn tableColumn = new TableColumn(names[i]);
            tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(indexColumn).toString()));
            table_result.getColumns().add(tableColumn);
            System.out.println("Column [" + i + "] ");
        }
        table_result.setItems(data);
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