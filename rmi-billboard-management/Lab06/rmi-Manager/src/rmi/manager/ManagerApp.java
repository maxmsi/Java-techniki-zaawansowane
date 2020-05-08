package rmi.manager;

import billboards.IBillboard;
import billboards.IClient;
import billboards.IManager;
import billboards.Order;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;

public class ManagerApp extends Application  {


    public ManagerApp() throws RemoteException, NotBoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws RemoteException, NotBoundException {

        VBox root = new VBox();

        Registry client_registry = LocateRegistry.getRegistry("localhost", 5555);
        IClient client_stub = (IClient) client_registry.lookup("IClient");

        Registry manager_registry = LocateRegistry.getRegistry("localhost", 5556);
        IManager manager_stub = (IManager) manager_registry.lookup("IManager");

        Registry billboard_registry = LocateRegistry.getRegistry("localhost", 5557);
        IBillboard billboard_stub = (IBillboard) billboard_registry.lookup("IBillboard");

        Label text_label = new Label("Dowiązanie tablice: ");
        Label billboard1 = new Label("1.  -Nie dowiązana.");
        Label interval_label = new Label("Ustaw interwal wyswietlania reklamy: ");
        TextField interval_field =  new TextField();
        Button set_interval = new Button("Ustaw interwał!");
        Button refresh = new Button("Przypisz tablice, aby mogła rozpocząć pracę! ");


        root.getChildren().addAll(text_label,interval_label,interval_field,set_interval,billboard1,refresh);
        primaryStage.setScene(new Scene(root,350,200));
        primaryStage.setTitle("Manager tablic");
        primaryStage.show();
        Duration duration = Duration.ofSeconds(3);

        set_interval.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Duration duration= Duration.ofSeconds(Long.parseLong(interval_field.getText().toString()));
                try {
                    billboard_stub.setDisplayInterval(duration);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        refresh.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        if(manager_stub.checkBillboardStatus()==true){
                            //przypisanie tablicy do menegara.
                            manager_stub.bindBillboard(billboard_stub);
                            billboard_stub.start();
                            billboard1.setText("1. Tablica przypisana. pracuje !");
                        }
                        else {
                            billboard1.setText("1.  -Nie dowiązana.");
                        }
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    }
                }
            });
        }


        }

