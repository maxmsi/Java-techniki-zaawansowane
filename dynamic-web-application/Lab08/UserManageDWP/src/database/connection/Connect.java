package database.connection;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.models.*;

public class Connect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static Statement stat;
	 private static Connection conn;
	 public static final String DRIVER = "org.sqlite.JDBC";
	 public static final String DB_URL = "jdbc:sqlite:/home/max/Dokumenty/eclipse-workspace/UserManageDWP/UserManage.db";
	
	public Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Connection conn = null;
		try {
			 conn = DriverManager.getConnection(DB_URL);
			 System.out.println("Connection has been established");
			 }	
		catch (SQLException e){
				 e.printStackTrace();
			 }
		return conn;
	}
	public  void connect() {
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
	public boolean createTables()  {
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
	public boolean insertUser(String imie, String nazwisko, String numeruzytkownika) {
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
	public boolean insertBills(String date, String dozaplaty,int id_user) {
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
    public boolean insertPayment(String date, String zaplacono,int id_user) {
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
    public boolean insertInstallation(String adres, String ruterNumber,String typuslugi,int id_user,int id_uslugi) {
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
    public boolean insertPriceList(String typuslugi,Double cena) {
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
    }
	
    public List<User> selectUsers() {
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
              users.add(new User(imie,nazwisko,numer_uzytkownika));

          }
      } catch (SQLException e) {
          e.printStackTrace();
          return null;
      }
      return users;

  }
    public List<Installation> selectInstallations(int id){
    	List<Installation> inst = new ArrayList<Installation>();
        try {
        	ResultSet result = stat.executeQuery("SELECT * FROM installations where id_user="+id);
            String addres,numerRutera,typ_usługi;
            int id_usługi,idinst,id_user;
            while(result.next()) {
                idinst = result.getInt("id_instalation");
                addres = result.getString("addres");
                numerRutera = result.getString("numerRutera");
                typ_usługi = result.getString("typ_usługi");
                id_usługi = result.getInt("id_uslugi");
                id_user = result.getInt("id_user");
                inst.add(new Installation(addres,numerRutera,typ_usługi,id_usługi,id_user));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
       return inst;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		PrintWriter out = response.getWriter();
 		out.print("Hello World");
 		
 	}
	public List<Bills> selectBillsByDate(int id, String date1, String date2) {
		List<Bills> bills = new ArrayList<Bills>();
        try {
        	ResultSet result = stat.executeQuery("SELECT * FROM bills where id_user="+id+" and data BETWEEN '"+date1+"' and '"+date2+"'");
            String data,dozaplaty;
            int id_user;
            while(result.next()) {   
                id_user = result.getInt("id_user");
                data = result.getString("data");
                dozaplaty= result.getString("dozaplaty");
                bills.add(new Bills(data,dozaplaty));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
       return bills;
	}
}
