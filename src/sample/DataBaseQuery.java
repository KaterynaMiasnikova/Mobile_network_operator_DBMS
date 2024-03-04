package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DataBaseQuery {
    public DataBaseQuery() {}

    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/tel?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }

    //authorization function
    public static boolean authorize(CurrentUser cur) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String CheckLog = String.format("SELECT job_ad, id_ad, surn_ad, name_ad, fath_ad FROM %s WHERE login_ad = '%s' AND pass_ad = '%s'", "adm", cur.getLoginCur(), cur.getPasswordCur());
        ResultSet result = statement.executeQuery(CheckLog);
        if (result.next()) {
            cur.setJob(result.getString(1));
            cur.setId(result.getInt(2));
            cur.setSurname(result.getString(3));
            cur.setName(result.getString(4));
            cur.setFathername(result.getString(5));
            return true;
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect login or password! Try again!");
            alert.showAndWait();
            return false;
        }
    }

    //function for obtaining the number of columns of the required table
    public static String[] columnsNumName(String tableName) throws SQLException, ClassNotFoundException {
        String[] array = new String[10];
        int i;
        Statement statement = getDbConnection().createStatement();
        String getTable = String.format("SELECT * FROM %s ", tableName);
        ResultSet rs = statement.executeQuery(getTable);
        ResultSetMetaData rsmd = rs.getMetaData();
        array[0] = String.valueOf(rsmd.getColumnCount());
        for (i = 1; i <= rsmd.getColumnCount(); i++) {
            array[i] = rs.getMetaData().getColumnName(i);
        }
        return array;
    }

    //the function of obtaining data from the database
    public static ObservableList<ObservableList> getTablesBySql(String sql) throws SQLException, ClassNotFoundException {
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println(resultSet.getMetaData().getColumnCount());
        while (resultSet.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                if (resultSet.getString(i) == null) {
                    row.add("-");
                } else row.add(resultSet.getString(i));
            }
            data.add(row);
        }
        return data;
    }

    //function to get the entire table
    public static ObservableList<ObservableList> getAllTable(String tableName) throws SQLException, ClassNotFoundException {
        String sql = String.format("SELECT * FROM %s", tableName);
        return getTablesBySql(sql);
    }

    //consultation table retrieval function
    public static ObservableList<ObservableList> getTableConsult() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_cn, dattim_cn, concat(abonent.surn_ab,' ',abonent.name_ab), concat(adm.surn_ad,' ' ,adm.name_ad), durat_cn, opys_cn\n" +
                "FROM consult join adm using(id_ad) join abonent using(id_ab)\n" +
                "Order by id_cn";
        return getTablesBySql(sql);
    }
    //function to get the customer table
    public static ObservableList<ObservableList> getTableClients() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_ab, surn_ab, name_ab, fath_ab, tel_ab, nazv_tr, vid_lg, sum\n" +
                "FROM abonent join tarif using(id_tr) left join lgota using(id_lg)\n" +
                "Order by id_ab";
        return getTablesBySql(sql);
    }
    //the function of receiving the table of payments
    public static ObservableList<ObservableList> getTablePlat() throws SQLException, ClassNotFoundException {
        String sql = "select id_pl, dattim_pl, concat(concat(surn_ab, ' '), name_ab) as abonent_name, nazv_tp, p.siz_tp\n" +
                "    from plat p\n" +
                "    join typplat t on t.id_tp = p.id_tp\n" +
                "    join abonent a on a.id_ab = p.id_ab\n" +
                "    order by id_pl";
        return getTablesBySql(sql);
    }
    //call table retrieval function
    public static ObservableList<ObservableList> getTableCall() throws SQLException, ClassNotFoundException {
        String sql = "select id_zv, dattim_zv, durat_zv, telfor_zv, concat(concat(surn_ab, ' '), name_ab) as abonent_name, nazv_vz, price_vz\n" +
                "from zvonok z \n" +
                "join vidzv v on v.id_vz = z.id_vz\n" +
                "join abonent a on a.id_ab = z.id_ab\n" +
                "order by id_zv;";
        return getTablesBySql(sql);
    }

    //1.1 Which clients did administrator X
    public static ObservableList<ObservableList> getConsultClients(String surname) throws SQLException, ClassNotFoundException {
        String sql = String.format("select abonent.surn_ab, abonent.name_ab, abonent.fath_ab, abonent.tel_ab from abonent\n" +
                "    join consult using (id_ab)\n" +
                "    join adm using (id_ad)\n" +
                "    where adm.surn_ad = '%s'", surname);
        return getTablesBySql(sql);
    }

    //1.2 What types of payments were made this month
    public static ObservableList<ObservableList> getThisMonthPay() throws SQLException, ClassNotFoundException {
        String sql = "select distinct tp.nazv_tp\n" +
                "    from (select id_tp from plat where month(dattim_pl) = month(now())) NQ\n" +
                "    join typplat tp using(id_tp)\n" +
                "    order by id_tp";
        return getTablesBySql(sql);
    }

    //2.1 Find all subscribers with a number starting with 067
    public static ObservableList<ObservableList> getClientsWithTel(String telStart) throws SQLException, ClassNotFoundException {
        telStart = telStart.concat("%");
        String sql = String.format("select distinct surn_ab, name_ab, fath_ab, tel_ab\n" +
                "    from abonent\n" +
                "    where tel_ab like '%s'", telStart);
        return getTablesBySql(sql);
    }

    //2.2 Find all consultations whose description begins with "Question"
    public static ObservableList<ObservableList> getQuestConsult(String line) throws SQLException, ClassNotFoundException {
        line = line.concat("%");
        String sql = String.format("select id_cn, dattim_cn, opys_cn\n" +
                "    from consult\n" +
                "    where opys_cn like '%s'", line);
        return getTablesBySql(sql);
    }

    //3.1 List of customers who made payments in a given period
    public static ObservableList<ObservableList> getPeriodPay(Date from, Date to) throws SQLException, ClassNotFoundException {
        String sql = String.format("select ab.surn_ab, ab.name_ab, ab.fath_ab, ab.tel_ab, nq.dattim_pl\n" +
                "    from(select * from plat where dattim_pl between '%tF' and '%tF') nq\n" +
                "    inner join abonent ab using(id_ab)\n" +
                "    order by nq.dattim_pl", from, to);
        return getTablesBySql(sql);
    }

    //3.2 List of administrators who conducted consultations in the given period
    public static ObservableList<ObservableList> getPeriodConsult(Date from, Date to) throws SQLException, ClassNotFoundException {
        String sql = String.format("select  adm.surn_ad, adm.name_ad, adm.fath_ad, nq.dattim_cn, ab.tel_ab\n" +
                "    from (select * from consult join adm using(id_ad) where dattim_cn between '%tF' and '%tF') nq\n" +
                "    inner join adm using(id_ad)\n" +
                "    inner join ab using(id_ab)\n" +
                "order by adm.surn_ad", from, to);
        return getTablesBySql(sql);
    }

    //4.1 How many subscribers are connected to the tariff Х
    public static ObservableList<ObservableList> getTarifAbonents(String tarif) throws SQLException, ClassNotFoundException {
        String sql = String.format("select count(*) as abonent_amount\n" +
                "    from (select id_tr from tarif where nazv_tr = '%s') NQ\n" +
                "    inner join abonent using(id_tr)", tarif);
        return getTablesBySql(sql);
    }

    //4.2 What amount was paid for the current year
    public static ObservableList<ObservableList> getThisYearSum() throws SQLException, ClassNotFoundException {
        String sql = "select sum(siz_tp)\n" +
                "    from (select * from plat where year(dattim_pl) = year(now())) NQ";
        return getTablesBySql(sql);
    }

    //5.1 How many subscribers did each administrator consult
    public static ObservableList<ObservableList> getAdmConsultClient() throws SQLException, ClassNotFoundException {
        String sql = "select adm.surn_ad, adm.name_ad, adm.fath_ad, count(*) as Abonents\n" +
                "    from adm\n" +
                "    inner join consult using(id_ad)\n" +
                "    group by adm.surn_ad";
        return getTablesBySql(sql);
    }

    //5.2 How many subscribers are connected to each benefit
    public static ObservableList<ObservableList> getExemptionClients() throws SQLException, ClassNotFoundException {
        String sql = "select lgota.vid_lg, count(*) as Abonents\n" +
                "    from lgota\n" +
                "    inner join abonent using(id_lg)\n" +
                "    group by lgota.vid_lg";
        return getTablesBySql(sql);
    }

    //6.1 Which subscriber made the most calls
    public static ObservableList<ObservableList> getMaxCalls() throws SQLException, ClassNotFoundException {
        String sql = "select ab.surn_ab, ab.name_ab, ab.fath_ab, ab.tel_ab, count(*) as Calls\n" +
                "    from  abonent ab\n" +
                "    join zvonok zv using(id_ab)\n" +
                "    group by ab.surn_ab\n" +
                "    having Calls >= all(select count(*) from zvonok group by id_ab)";
        return getTablesBySql(sql);
    }

    //6.2 What type of payments were made the most times
    public static ObservableList<ObservableList> getMaxTypPlat() throws SQLException, ClassNotFoundException {
        String sql = " select typplat.nazv_tp, count(*) as Platezhy\n" +
                "    from typplat\n" +
                "    join plat using(id_tp)\n" +
                "    group by typplat.nazv_tp\n" +
                "    having Platezhy >= all(select count(*) from plat group by id_tp)";
        return getTablesBySql(sql);
    }

    //7.1 For each tariff, determine the type of call that was made the largest number of times
    public static ObservableList<ObservableList> getMaxTarifCall() throws SQLException, ClassNotFoundException {
        String sql = " select t1.nazv_tr, vidzv.nazv_vz, count(*) as Amount\n" +
                "    from tarif t1\n" +
                "    join vidzv using(id_tr)\n" +
                "    join zvonok using(id_vz)\n" +
                "    group by t1.nazv_tr, vidzv.nazv_vz\n" +
                "    having Amount >= all(select count(*) from tarif t2 join vidzv using(id_tr) join zvonok using(id_vz)\n" +
                "    where t2.nazv_tr = t1.nazv_tr\n" +
                "    group by vidzv.nazv_vz)\n" +
                "    order by t1.nazv_tr";
        return getTablesBySql(sql);
    }

    //7.2 For each administrator, determine the subscriber whom he consulted the most number of times
    public static ObservableList<ObservableList> getMaxAdminClient() throws SQLException, ClassNotFoundException {
        String sql = "select a1.surn_ad, a1.name_ad, a1.fath_ad, count(*) as Amount, ab.surn_ab, ab.name_ab, ab.fath_ab, ab.tel_ab\n" +
                "    from adm a1\n" +
                "    join consult using(id_ad)\n" +
                "    join abonent ab using(id_ab)\n" +
                "    group by a1.id_ad\n" +
                "    having Amount >= all(select count(*) from adm a2 join consult using(id_ad) join abonent ab using(id_ab)\n" +
                "    where a1.id_ad = a2.id_ad\n" +
                "    group by consult.id_ad)\n" +
                "    order by a1.id_ad";
        return getTablesBySql(sql);
    }

    //8.1 Which subscribers have never sought advice
    public static ObservableList<ObservableList> getNeverConsult() throws SQLException, ClassNotFoundException {
        String sql = "select ab.surn_ab, ab.name_ab, ab.fath_ab, ab.tel_ab\n" +
                "    from abonent ab\n" +
                "    left join consult using(id_ab)\n" +
                "    where id_cn is null";
        return getTablesBySql(sql);
    }

    //8.2 What types of payments were not made in the last year
    public static ObservableList<ObservableList> getThisYearNoTypPlat() throws SQLException, ClassNotFoundException {
        String sql = "select typplat.nazv_tp\n" +
                "    from (select * from plat where year(dattim_pl) = year(now())) NQ\n" +
                "    right join typplat using(id_tp)\n" +
                "    where id_pl is null";
        return getTablesBySql(sql);
    }

    //9.1 List of subscribers with comments "Most consulted" and "Never consulted"
    public static ObservableList<ObservableList> getMaxMinConsult() throws SQLException, ClassNotFoundException {
        String sql = "select ab.surn_ab, ab.name_ab, ab.fath_ab, ab.tel_ab, 'Most consulted' as commentary\n" +
                "    from abonent ab join consult c using (id_ab)\n" +
                "    group by id_ab\n" +
                "    having count(*) >= all(select count(*) from consult group by id_ab)\n" +
                "    union\n" +
                "    select ab.surn_ab, ab.name_ab, ab.fath_ab, ab.tel_ab, 'Never consulted'\n" +
                "    from abonent ab\n" +
                "    left join consult using(id_ab)\n" +
                "    where id_cn is null";
        return getTablesBySql(sql);
    }

    //9.2 Tariff list with comments "Connected the largest number of subscribers" and "Connected the smallest number of customers"
    public static ObservableList<ObservableList> getMaxMinTarif() throws SQLException, ClassNotFoundException {
        String sql = "select tarif.nazv_tr, 'Connected the largest number of subscribers' as commentary\n" +
                "    from tarif join abonent using (id_tr)\n" +
                "    group by id_tr\n" +
                "    having count(*) >= all(select count(*) from abonent group by id_tr)\n" +
                "    union\n" +
                "    select tarif.nazv_tr, 'Connected the smallest number of customers'\n" +
                "    from tarif join abonent using (id_tr)\n" +
                "    group by id_tr\n" +
                "    having count(*) <= all(select count(*) from abonent group by id_tr)";
        return getTablesBySql(sql);
    }

    public static void addConsultation(String telnum, String opys, String durat) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        Integer t = Integer.parseInt(telnum);
        double d = Double.parseDouble(durat);
        String insert = String.format("INSERT INTO tel.consult (%s,%s,%s,%s) VALUES (?,?,?,?)", "id_ab", "id_ad", "durat_cn", "opys_cn");
        String sql = String.format("SELECT id_ab FROM tel.abonent WHERE tel_ab = '%s'", t);
        ResultSet rs = statement.executeQuery(sql);
        int idAb = 0;
        while (rs.next()) {
            idAb = rs.getInt(1);
        }
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1, idAb);
            preparedStatement.setInt(2, CurrentUser.getId());
            preparedStatement.setDouble(3, d);
            preparedStatement.setString(4, opys);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException var6) {
            var6.printStackTrace();
        }
        getDbConnection().close();
    }

    public static List<String> getTarifs() throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String SelectConcert = String.format("SELECT nazv_tr FROM %s ", "tarif");
        List<String> names = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery(SelectConcert)) {
            while (resultSet.next()) {
                names.add(resultSet.getString("nazv_tr"));
            }
            return names;
        }
    }

    public static List<String> getLgoty() throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String SelectConcert = String.format("SELECT vid_lg FROM %s ", "lgota");
        List<String> names = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery(SelectConcert)) {
            while (resultSet.next()) {
                names.add(resultSet.getString("vid_lg"));
            }
            names.add("No benefit");
            return names;
        }
    }

    public static void editTarifLgota(Integer id, String tr, String lg) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String insert = String.format("UPDATE tel.abonent SET %s = ?, %s = ? WHERE id_ab = %d", "id_lg", "id_tr", id);
        String sqlT = String.format("SELECT id_tr FROM tel.tarif WHERE nazv_tr = '%s'", tr);
        ResultSet rs = statement.executeQuery(sqlT);
        int idT = 0;
        while (rs.next()) {
            idT = rs.getInt(1);
        }
        if(!lg.equals("No benefit")) {
            String sqlL = String.format("SELECT id_lg FROM tel.lgota WHERE vid_lg = '%s'", lg);
            rs = statement.executeQuery(sqlL);
            int idL = 0;
            while (rs.next()) {
                idL = rs.getInt(1);
            }
            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
                preparedStatement.setInt(1, idL);
                preparedStatement.setInt(2, idT);
                preparedStatement.executeUpdate();
            } catch (ClassNotFoundException | SQLException var6) {
                var6.printStackTrace();
            }
        } else {
            try {
                insert = String.format("UPDATE tel.abonent SET %s = null, %s = ? WHERE id_ab = %d", "id_lg", "id_tr", id);
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
                preparedStatement.setInt(1, idT);
                preparedStatement.executeUpdate();
            } catch (ClassNotFoundException | SQLException var6) {
                var6.printStackTrace();
            }
        }
        System.out.println("Success");
    }
    public static void editTarifLgota(Integer id, Integer tr, Integer lg) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String insert = String.format("UPDATE tel.abonent SET %s = ?, %s = ? WHERE id_ab = %d", "id_lg", "id_tr", id);
        if(lg != null) {
            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
                preparedStatement.setInt(1, tr);
                preparedStatement.setInt(2, lg);
                preparedStatement.executeUpdate();
            } catch (ClassNotFoundException | SQLException var6) {
                var6.printStackTrace();
            }
        } else {
            try {
                insert = String.format("UPDATE tel.abonent SET %s = null, %s = ? WHERE id_ab = %d", "id_lg", "id_tr", id);
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
                preparedStatement.setInt(1, tr);
                preparedStatement.executeUpdate();
            } catch (ClassNotFoundException | SQLException var6) {
                var6.printStackTrace();
            }
        }
        System.out.println("Success");
    }

    public static void deleteRowFromTable(Integer id) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String d_id = switch (StaticVariables.getWindow()) {
            case ("abonent") -> "id_ab";
            case ("adm") -> "id_ad";
            default -> "";
        };
        String sql = String.format("Delete FROM %s WHERE %s = %d", StaticVariables.getWindow(), d_id, id);
        statement.executeUpdate(sql);
    }
    public static boolean addAdm(String name, String surname, String fathersname, String login, String password, String job, String address, String salary) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String checkExistance = String.format("SElECT id_ad FROM %s WHERE login_ad ='%s' ", "adm", login);
        ResultSet result = statement.executeQuery(checkExistance);
        boolean b = false;
        try {
            if (!result.next()) {
                String insert = String.format("INSERT INTO tel.adm (%s,%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?,?)", "surn_ad", "name_ad", "fath_ad", "login_ad", "pass_ad", "adress_ad", "stavka_ad", "job_ad");
                try {
                    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
                    preparedStatement.setString(1, surname);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, fathersname);
                    preparedStatement.setString(4, login);
                    preparedStatement.setString(5, password);
                    preparedStatement.setString(6, address);
                    preparedStatement.setString(7, salary);
                    preparedStatement.setString(8, job);
                    preparedStatement.executeUpdate();
                    b = true;
                } catch (ClassNotFoundException | SQLException var6) {
                    var6.printStackTrace();
                }
                System.out.println("Success");
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An employee with this login was added earlier!");
                alert.showAndWait();
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        }
        getDbConnection().close();
        return b;
    }
    public static boolean changeAdm(Integer id, String name, String surname, String fathersname, String login, String password, String job, String address, String salary) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String checkExistence = String.format("SElECT id_ad FROM %s WHERE login_ad ='%s' ", "adm", login);
        ResultSet result = statement.executeQuery(checkExistence);
        boolean b = false;
        try {
            boolean f = result.next();
            if (!f) {
                String insert = String.format("UPDATE tel.adm SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE id_ad = %d", "surn_ad", "name_ad", "fath_ad", "login_ad", "pass_ad", "adress_ad", "stavka_ad", "job_ad", id);
                try {
                    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
                    preparedStatement.setString(1, surname);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, fathersname);
                    preparedStatement.setString(4, login);
                    preparedStatement.setString(5, password);
                    preparedStatement.setString(6, address);
                    preparedStatement.setString(7, salary);
                    preparedStatement.setString(8, job);
                    preparedStatement.executeUpdate();
                    b = true;
                } catch (ClassNotFoundException | SQLException var6) {
                    var6.printStackTrace();
                }
                System.out.println("Success");
            } else {
                String str = result.getString(1);
                if (str.equals(String.valueOf(id))) {
                    String insert = String.format("UPDATE tel.adm SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE id_ad = %d", "surn_ad", "name_ad", "fath_ad", "login_ad", "pass_ad", "adress_ad", "stavka_ad", "job_ad", id);
                    try {
                        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
                        preparedStatement.setString(1, surname);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, fathersname);
                        preparedStatement.setString(4, login);
                        preparedStatement.setString(5, password);
                        preparedStatement.setString(6, address);
                        preparedStatement.setString(7, salary);
                        preparedStatement.setString(8, job);
                        preparedStatement.executeUpdate();
                        b = true;
                    } catch (ClassNotFoundException | SQLException var6) {
                        var6.printStackTrace();
                    }
                    System.out.println("Success");
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("An employee with this login was added earlier!");
                    alert.showAndWait();
                }
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        }
        getDbConnection().close();
        return b;
    }
    public static boolean addAbonent(String surname, String name, String fathersname, String tel, int id_tr, int id_lg) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String checkExistance = String.format("SElECT id_ab FROM %s WHERE tel_ab ='%s'", "abonent", tel);
        ResultSet result = statement.executeQuery(checkExistance);
        boolean b = false;
        try {
            if (!result.next()) {
                String insert = String.format("INSERT INTO tel.abonent (%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?)", "surn_ab", "name_ab", "fath_ab", "tel_ab", "id_tr", "id_lg");
                try {
                    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
                    preparedStatement.setString(1, surname);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, fathersname);
                    preparedStatement.setString(4, tel);
                    preparedStatement.setInt(5, id_tr);
                    preparedStatement.setInt(6, id_lg);
                    preparedStatement.executeUpdate();
                    b = true;
                } catch (ClassNotFoundException | SQLException var6) {
                    var6.printStackTrace();
                }
                System.out.println("Success");
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("A customer with this number was added earlier!");
                alert.showAndWait();
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        }
        getDbConnection().close();
        return b;
    }
    public static boolean changeAbonent(Integer id, String surname, String name, String fathersname, String tel, Integer id_tr, Integer id_lg) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String checkExistance = String.format("SElECT id_ab FROM %s WHERE tel_ab ='%s'", "abonent", tel);
        ResultSet result = statement.executeQuery(checkExistance);
        boolean b = false;
        try {
            boolean f = result.next();
            if (!f) {
                String insert = String.format("UPDATE tel.abonent SET %s = ?, %s = ?, %s = ?, %s = ? WHERE id_ab = %d", "surn_ab", "name_ab", "fath_ab", "tel_ab", id);
                try {
                    PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
                    preparedStatement.setString(1, surname);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, fathersname);
                    preparedStatement.setString(4, tel);
                    preparedStatement.executeUpdate();
                    editTarifLgota(id, id_tr, id_lg);
                    b = true;
                } catch (ClassNotFoundException | SQLException var6) {
                    var6.printStackTrace();
                }
                System.out.println("Success");
            } else {
                String str = result.getString(1);
                if (str.equals(String.valueOf(id))) {
                    String insert = String.format("UPDATE tel.abonent SET %s = ?, %s = ?, %s = ?, %s = ? WHERE id_ab = %d", "surn_ab", "name_ab", "fath_ab", "tel_ab", id);
                    try {
                        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
                        preparedStatement.setString(1, surname);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, fathersname);
                        preparedStatement.setString(4, tel);
                        preparedStatement.executeUpdate();
                        editTarifLgota(id, id_tr, id_lg);
                        b = true;
                    } catch (ClassNotFoundException | SQLException var6) {
                        var6.printStackTrace();
                    }
                    System.out.println("Success");
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("A customer with this number was added earlier!");
                    alert.showAndWait();
                }
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        }
        getDbConnection().close();
        return b;
    }
    public static boolean addTarif(String nazv_tr, double price_tr, int inet_tr, int time_tr, int tfor_tr) throws SQLException, ClassNotFoundException {
        boolean b = false;
        String insert = String.format("INSERT INTO tel.tarif (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)", "nazv_tr", "price_tr", "inet_tr", "time_tr", "tfor_tr");
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, nazv_tr);
            preparedStatement.setDouble(2, price_tr);
            preparedStatement.setInt(3, inet_tr);
            preparedStatement.setInt(4, time_tr);
            preparedStatement.setInt(5, tfor_tr);
            preparedStatement.executeUpdate();
            b = true;
        } catch (ClassNotFoundException | SQLException var6) {
            var6.printStackTrace();
        }
        System.out.println("Success");
        getDbConnection().close();
        return b;
    }
    public static boolean changeTarif(Integer id, String nazv_tr,double price_tr, int inet_tr, int time_tr, int tfor_tr) throws SQLException, ClassNotFoundException {
        boolean b = false;
        String insert = String.format("UPDATE tel.tarif SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE id_tr = %d", "nazv_tr", "price_tr", "inet_tr", "time_tr", "tfor_tr", id);
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, nazv_tr);
            preparedStatement.setDouble(2, price_tr);
            preparedStatement.setInt(3, inet_tr);
            preparedStatement.setInt(4, time_tr);
            preparedStatement.setInt(5, tfor_tr);
            preparedStatement.executeUpdate();
            b = true;
        } catch (ClassNotFoundException | SQLException var6) {
            var6.printStackTrace();
        }
        System.out.println("Success");
        getDbConnection().close();
        return b;
    }
    public static boolean addLgota(String vid_lg, double siz_lg) throws SQLException, ClassNotFoundException {
        boolean b = false;
        String insert = String.format("INSERT INTO tel.lgota (%s,%s) VALUES (?,?)", "vid_lg", "siz_lg");
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, vid_lg);
            preparedStatement.setDouble(2, siz_lg);
            preparedStatement.executeUpdate();
            b = true;
        } catch (ClassNotFoundException | SQLException var6) {
            var6.printStackTrace();
        }
        System.out.println("Success");
        getDbConnection().close();
        return b;
    }

    //1. Report on the number of subscribers consulted by each employee
    public static ObservableList<String> reportAmountAdConsult() throws SQLException, ClassNotFoundException {
        String sql = String.format("select adm.surn_ad, adm.name_ad, concat(adm.fath_ad, ':'), concat(count(*), ' abonents consulted') as Abonents\n" +
                "from adm\n" +
                "inner join consult using(id_ad)\n" +
                "group by adm.surn_ad\n" +
                "union all\n" +
                "select \"Sum:\",\"\",\"\", count(*) as Abonents\n" +
                "from adm\n" +
                "inner join consult using(id_ad)\n");
        return getReportBySQL(sql);
    }
    //2. Report on the number of subscribers connected to each benefit
    public static ObservableList<String> reportAmountAbLg() throws SQLException, ClassNotFoundException {
        String sql = String.format("select concat(lgota.vid_lg, ':'), concat(count(*), ' abonents connected') as Abonents\n" +
                "from lgota\n" +
                "inner join abonent using(id_lg)\n" +
                "group by lgota.vid_lg\n" +
                "union all\n" +
                "select \"Sum:\", count(*) as Abonents\n" +
                "from lgota\n" +
                "inner join abonent using(id_lg)");
        return getReportBySQL(sql);
    }
    //3. Report by type of call for each tariff, which was made the largest number of times
    public static ObservableList<String> reportMaxCallTarif() throws SQLException, ClassNotFoundException {
        String sql = String.format("select concat('Tariff ', t1.nazv_tr), concat(concat('- type of call ', vidzv.nazv_vz), ':'), concat(count(*), ' times made') as Amount\n" +
                "from tarif t1\n" +
                "join vidzv using(id_tr)\n" +
                "join zvonok using(id_vz)\n" +
                "group by t1.nazv_tr, vidzv.nazv_vz\n" +
                "having Amount >= all(select count(*) from tarif t2 join vidzv using(id_tr) join zvonok using(id_vz)\n" +
                "where t2.nazv_tr = t1.nazv_tr\n" +
                "group by vidzv.nazv_vz)\n" +
                "order by t1.nazv_tr\n");
        return getReportBySQL(sql);
    }
    //4. Report on subscribers who were consulted the most times by each employee
    public static ObservableList<String> reportAdMaxConsultAb() throws SQLException, ClassNotFoundException {
        String sql = String.format("select a1.surn_ad, a1.name_ad, a1.fath_ad, count(*) as Amount, ab.surn_ab, ab.name_ab, ab.fath_ab, ab.tel_ab\n" +
                "from adm a1\n" +
                "join consult using(id_ad)\n" +
                "join abonent ab using(id_ab)\n" +
                "group by a1.id_ad\n" +
                "having Amount >= all(select count(*) from adm a2 join consult using(id_ad) join abonent ab using(id_ab)\n" +
                "where a1.id_ad = a2.id_ad\n" +
                "group by consult.id_ad)\n" +
                "order by a1.id_ad\n");
        return getReportBySQL(sql);
    }
    //5. Report on subscribers who requested consultations the largest number of times and did not contact at all.
    public static ObservableList<String> reportMaxNoConsult() throws SQLException, ClassNotFoundException {
        String sql = String.format("select ab.surn_ab, ab.name_ab, ab.fath_ab, ab.tel_ab, concat('Requested consultations most times: ',count(*)) as commentary\n" +
                "from abonent ab join consult c using (id_ab)\n" +
                "group by id_ab\n" +
                "having count(*) >= all(select count(*) from consult group by id_ab)\n" +
                "union select ab.surn_ab, ab.name_ab, ab.fath_ab, ab.tel_ab, 'Never asked for advice'\n" +
                "from abonent ab\n" +
                "left join consult using(id_ab)\n" +
                "where id_cn is null\n");
        return getReportBySQL(sql);
    }
    //6. Report on tariffs to which the largest and smallest number of subscribers are connected
    public static ObservableList<String> reportMaxMinTarif() throws SQLException, ClassNotFoundException {
        String sql = String.format("select tarif.nazv_tr, concat('The largest number of subscribers are connected: ',count(*)) as commentary\n" +
                "from tarif join abonent using (id_tr)\n" +
                "group by id_tr\n" +
                "having count(*) >= all(select count(*) from abonent group by id_tr)\n" +
                "union select tarif.nazv_tr, concat('Fewest number of subscribers connected: ',count(*))\n" +
                "from tarif join abonent using (id_tr)\n" +
                "group by id_tr\n" +
                "having count(*) <= all(select count(*) from abonent group by id_tr)\n");
        return getReportBySQL(sql);
    }

    public static ObservableList<String> getReportBySQL(String sql) throws SQLException, ClassNotFoundException {
        ObservableList<String> a = FXCollections.observableArrayList();
        Statement statement = getDbConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String temp = " ";
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                temp = temp.concat(" " + rs.getString(i));
            }
            a.add(temp);
        }
        return a;
    }
}