package ManagementApp.Controllers;

import database.setup_queries.Connect;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceListController {

    public TableView pricelist_tableview;
    private ObservableList<ObservableList> data;
    static Connect database=new Connect();

    public TextField id_pricelist_field;
    public TextField typ_field;
    public TextField cena_field;
    public Button add_button;
    public Button delete_button;
    public Button edit_button;

    public void initialize() throws Exception {
        handlingButtons();
        build_data();
    }
    public void build_data(){

        data = FXCollections.observableArrayList();
        try{

            ResultSet rs = database.selectPriceListRS();

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            pricelist_tableview.getColumns().clear();

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                pricelist_tableview.getColumns().addAll(col);
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
            pricelist_tableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    };
    public void handlingButtons(){
        add_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                database.insertPriceList(typ_field.getText(),Double.parseDouble(cena_field.getText()));
                build_data();
            }
        });
        delete_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    database.deletePriceList(Integer.parseInt(id_pricelist_field.getText()));
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
                    database.editPriceList(Integer.parseInt(id_pricelist_field.getText()),typ_field.getText(),Double.parseDouble(cena_field.getText()));
                    build_data();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }
    
    
    
}
