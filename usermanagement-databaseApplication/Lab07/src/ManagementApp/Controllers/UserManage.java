package ManagementApp.Controllers;

import database.setup_queries.Connect;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManage {

    private ObservableList<ObservableList> data;
    static Connect database=new Connect();

    @FXML TableView user_tableview;
    @FXML Button delete_button;
    @FXML Button add_button;
    @FXML Button edit_button;

    @FXML TextField id_user_field;
    @FXML TextField imie_field;
    @FXML TextField nazwisko_field;
    @FXML TextField numer_field;

    public void initialize(){
        build_data();
        handlingButtons();
    }
    public void build_data(){

        data = FXCollections.observableArrayList();
        try{

            ResultSet rs = database.selectUsersRS();

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            user_tableview.getColumns().clear();

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                user_tableview.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }
            //FINALLY ADDED TO TableView
            user_tableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    };
    public void handlingButtons(){
        add_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            database.insertUser(imie_field.getText(),nazwisko_field.getText(),numer_field.getText());
            build_data();
            }
        });
        delete_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    database.deleteUser(Integer.parseInt(id_user_field.getText()));
                    build_data();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        edit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    database.editUser(Integer.parseInt(id_user_field.getText()),imie_field.getText(),nazwisko_field.getText(),numer_field.getText());
                    build_data();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }



}