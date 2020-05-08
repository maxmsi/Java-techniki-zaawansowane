package ManagementApp;

import database.models.User;
import database.setup_queries.Connect;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    public Button check_bills_button;
    public Button check_payments_button;
    public Button back_button;
    public Button payments_button;
    @FXML
    Button data_manage_button;
    @FXML
    Button installations_button;
    @FXML
    Button pricelist_button;
    @FXML TableView user_tableview;

    public Object DataManageController;
    private ObservableList<ObservableList> data;
    static Connect database=new Connect();
    public Object PriceListController;

    public void initialize() throws Exception {
        handlingButtons();
        build_data(database.selectUsersRS());
    }
    public void build_data(ResultSet x){

        data = FXCollections.observableArrayList();
        data.clear();
        try{
            ResultSet rs = x;

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            user_tableview.getColumns().clear();
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
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
        data_manage_button.setOnMouseClicked((event) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                //fxmlLoader.setLocation(getClass().getResource("/ManagementApp/DataManage.fxml"));
                fxmlLoader.setLocation(getClass().getResource("Views/UsersManage.fxml"));
                 //if "fx:controller" is not set in fxml
                fxmlLoader.setController(DataManageController);

                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setTitle("Zarzadzanie uzytkownikami");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
        });
        installations_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    //fxmlLoader.setLocation(getClass().getResource("/ManagementApp/DataManage.fxml"));
                    fxmlLoader.setLocation(getClass().getResource("Views/DataManage.fxml"));
                    //if "fx:controller" is not set in fxml
                    fxmlLoader.setController(DataManageController);

                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Zarzadzanie instalacjami");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
            }
        });
        pricelist_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    //fxmlLoader.setLocation(getClass().getResource("/ManagementApp/DataManage.fxml"));
                    fxmlLoader.setLocation(getClass().getResource("Views/PriceList.fxml"));
                    //if "fx:controller" is not set in fxml
                    fxmlLoader.setController(PriceListController);
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Zarzadzanie cennikiem");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
            }
        });
        check_bills_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = user_tableview.getSelectionModel().getSelectedItem().toString();
                int i = user.indexOf(',');
                String id = user.substring(1, i);
                try {
                    ResultSet rs =database.selectBillsRSbyID(Integer.parseInt(id));
                    build_data(rs);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        check_payments_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = user_tableview.getSelectionModel().getSelectedItem().toString();
                int i = user.indexOf(',');
                String id = user.substring(1, i);
                try {
                    ResultSet rs =database.selectPaymentsRSbyID(Integer.parseInt(id));
                    build_data(rs);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        back_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    build_data(database.selectUsersRS());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        payments_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    //fxmlLoader.setLocation(getClass().getResource("/ManagementApp/DataManage.fxml"));
                    fxmlLoader.setLocation(getClass().getResource("Views/UserPayments.fxml"));
                    //if "fx:controller" is not set in fxml
                    fxmlLoader.setController(PriceListController);
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Zarzadzanie oplatami");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", e);
                }
            }
        });
    }

}
