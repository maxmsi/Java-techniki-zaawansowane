package ManagementApp.Controllers;

import database.models.User;
import database.setup_queries.Connect;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManageController {

    public TextField id_installation_field;
    public TextField addres_field;
    public TextField routherNumber_field;
    public TextField serviceType_field;
    public TextField id_user_field;
    public TextField iduslugi_field;
    private ObservableList<ObservableList> data;
    //@FXML TableView installation_tableview;
    static Connect database = new Connect();
    @FXML
    TableView installation_tableview;
    @FXML
    Button delete_button, edit_button, add_button;

    public void initialize() {
        buildData();
        handlingButtons();
    }

    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs;
            rs = database.selectInstalationRS();

            installation_tableview.getColumns().clear();

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                installation_tableview.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }
            //FINALLY ADDED TO TableView
            installation_tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }


    public void handlingButtons() {
        add_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                database.insertInstallation(addres_field.getText(), routherNumber_field.getText(), serviceType_field.getText(), Integer.parseInt(id_user_field.getText()),Integer.parseInt(iduslugi_field.getText()));
                buildData();
            }
        });

        delete_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    database.deleteInstallation(Integer.parseInt(id_installation_field.getText()));
                    buildData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        edit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    database.editInstallation(Integer.parseInt(id_installation_field.getText()),addres_field.getText(), routherNumber_field.getText(), serviceType_field.getText(), Integer.parseInt(id_user_field.getText()), Integer.parseInt(iduslugi_field.getText()));
                    buildData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }
}
