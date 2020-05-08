package database.setup_queries;

import database.models.User;
import dao.implementations.UserDAO;
import dao.api.DAO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class Connect {
    private static DAO<User> userDAO=new UserDAO();
    private static List<User> user;


    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:UserManagement.db";
    private static Statement stat;
    private static Connection conn;



    public Connect() {

        connect();
        createTables();
    }
    static {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void connect() {
            Connection conn = null;
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
                System.err.println("Brak sterownika JDBC");
                e.printStackTrace();
            }
            try {
                conn = DriverManager.getConnection(DB_URL);
                stat = conn.createStatement();
            } catch (SQLException e) {
                System.err.println("Problem z otwarciem polaczenia");
                e.printStackTrace();
            }
        }

        //create Tables Querie
    public static boolean createTables()  {
        String createUsers = "CREATE TABLE IF NOT EXISTS users (id_user INTEGER PRIMARY KEY AUTOINCREMENT, imie varchar(255), nazwisko varchar(255), numeruzytkownia varchar(255))";
        String createBills = "CREATE TABLE IF NOT EXISTS bills (id_bill INTEGER PRIMARY KEY AUTOINCREMENT, data varchar(255), dozaplaty varchar(255),id_user INTEGER,FOREIGN KEY(id_user) REFERENCES users(id_user))";
        String createPriceList = "CREATE TABLE IF NOT EXISTS pricelist (id_pricelist INTEGER PRIMARY KEY AUTOINCREMENT, typ_uslugi varchar(255), cena real)";
        String createPayments = "CREATE TABLE IF NOT EXISTS payments (id_payment INTEGER PRIMARY KEY AUTOINCREMENT, data date, wartosczaplacono real,id_user INTEGER,FOREIGN KEY(id_user) REFERENCES users(id_user))";
        String createInstallation = "CREATE TABLE IF NOT EXISTS installations (id_instalation INTEGER PRIMARY KEY AUTOINCREMENT, addres varchar(255), numerRutera varchar(255),typ_usługi varchar(255),id_uslugi integer,id_user INTEGER,FOREIGN KEY(id_user) REFERENCES users(id_user),FOREIGN KEY(id_uslugi) REFERENCES pricelist(id_pricelist))";

        try {
            stat.execute(createUsers);
            stat.execute(createBills);
            stat.execute(createPriceList);
            stat.execute(createPayments);
            stat.execute(createInstallation);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //insert Queries
    public static boolean insertUser(String imie, String nazwisko, String numeruzytkownika) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into users values (NULL, ?, ?, ?);");
            prepStmt.setString(1, imie);
            prepStmt.setString(2, nazwisko);
            prepStmt.setString(3, numeruzytkownika);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu uzytkownika");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean insertBills(String date, String dozaplaty,int id_user) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into bills values (NULL, ?, ?, ?);");
            prepStmt.setString(1, date);
            prepStmt.setString(2, dozaplaty);
            prepStmt.setInt(3, id_user);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu rachunku");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean insertPayment(String date, String zaplacono,int id_user) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into payments values (NULL, ?, ?, ?);");
            prepStmt.setString(1, date);
            prepStmt.setString(2, zaplacono);
            prepStmt.setInt(3, id_user);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu platnosci");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean insertInstallation(String adres, String ruterNumber,String typuslugi,int id_user,int id_uslugi) {
        try {


            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into installations values (NULL, ?, ?, ?,?,?);");
            prepStmt.setString(1, adres);
            prepStmt.setString(2, ruterNumber);
            prepStmt.setString(3, typuslugi);
            prepStmt.setInt(4, id_uslugi);
            prepStmt.setInt(5, id_user);
            prepStmt.execute();
            prepStmt.close();

            PreparedStatement prepStmt2 = conn.prepareStatement(
                    "select cena from pricelist where id_pricelist="+id_uslugi
            );
            ResultSet cena=prepStmt2.executeQuery();
            String pattern = "YYYY-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            insertBills(date,cena.getString(1),id_user);
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu instalacji");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean insertPriceList(String typuslugi,Double cena) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into pricelist values (NULL, ?, ?);");
            prepStmt.setString(1, typuslugi);
            prepStmt.setDouble(2, cena);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu uslugi");
            e.printStackTrace();
            return false;
        }
        return true;
    }/**

    // select queries*/
    public static List<User> selectUsers() {
        List<User> users = new ArrayList<User>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM users");
            int id;
            String imie, nazwisko,numer_uzytkownika;
            while(result.next()) {
                id = result.getInt("id_user");
                imie = result.getString("imie");
                nazwisko = result.getString("nazwisko");
                numer_uzytkownika = result.getString("numeruzytkownia");

                //users.add(new User(imie,nazwisko,numer_uzytkownika));
                userDAO.save(new User(imie,nazwisko,numer_uzytkownika));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return userDAO.getAll();
    }
    public static ResultSet selectUsersRS() throws SQLException {
        ResultSet result = stat.executeQuery("SELECT * FROM users");
        return result;
    }
    public static ResultSet selectInstalationRS() throws SQLException {
        ResultSet result = stat.executeQuery("SELECT * FROM installations");
        return result;
    }
    public static ResultSet selectPriceListRS() throws SQLException {
        ResultSet result = stat.executeQuery("SELECT * FROM pricelist");
        return result;
    }
    public static ResultSet selectBillsRSbyID(int id) throws SQLException{
        return stat.executeQuery("select id_bill, data, dozaplaty, id_user from bills where id_user="+ id);
    }
    public static ResultSet selectPaymentsRSbyID(int id) throws SQLException{

        return stat.executeQuery("select id_PAYMENT, data, wartosczaplacono, id_user from payments where id_user="+ id);
    }
    public static ResultSet selectPaymentsRS() throws SQLException{

        return stat.executeQuery("select * from payments");
    }

    //delete statements
    public static void deleteUser(int id) throws SQLException {
        stat.execute("DELETE FROM users WHERE id_user ="+id);
    }
    public static void deleteInstallation(int id) throws SQLException {
        stat.execute("DELETE FROM installations WHERE id_instalation ="+id);
    }
    public static void deletePriceList(int id) throws SQLException {
         stat.execute("DELETE FROM pricelist WHERE id_pricelist="+id);
    }
    public static void deletePayments(int id) throws SQLException{
        stat.execute("DELETE FROM payments WHERE id_payment="+id);
    }

    //edit queires
    public void editUser(int id,String imie,String nazwisko,String nruser) throws SQLException{
        stat.execute("UPDATE users SET imie ='"+imie+"',nazwisko='"+nazwisko+"', numeruzytkownia='"+nruser+"'WHERE id_user = "+id);
    }
    public void editInstallation(int id, String addres, String routherNumber, String serviceType, int idUser, int id_user) throws SQLException{
        stat.execute("UPDATE installations SET addres ='"+addres+"',numerRutera='"+routherNumber+"', typ_usługi='"+serviceType+"',id_user="+id_user+" WHERE id_instalation = "+id);
    }
    public void editPriceList (int id, String typ_uslugi,double cena) throws SQLException {
        stat.execute("UPDATE pricelist SET typ_uslugi='"+typ_uslugi+"',cena="+cena+" where id_pricelist ="+id);
    }
    public void editPayment(int id, Date date, Double wartosczaplacono,int id_user) throws SQLException {
        stat.execute("UPDATE payments SET data='"+date+"', wartosczaplacono="+wartosczaplacono+",id_user="+id_user+" where id_payment="+id);
    }


        public static void main(String[] args) {
//            connect();
//            createTables();
////            insertUser("Kamil","Test","232hgf2");
////            insertUser("Kamil","Test","22shgfe2");
                //insertBills("21/21/21","213.32",15);
////            insertPayment("22/21/21","232",1);
////            insertInstallation("x","x","x",1,1);
////            insertPriceList("naprawa rutera",210.0);
////            selectUsers();
        }

}
