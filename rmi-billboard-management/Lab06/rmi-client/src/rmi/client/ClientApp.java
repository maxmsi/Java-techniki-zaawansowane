package rmi.client;

import billboards.IBillboard;
import billboards.IClient;
import billboards.IManager;
import billboards.Order;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;

public class ClientApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @FXML
    Button allow_button = new Button();
    @FXML
    TextArea duration_text= new TextArea();
    @FXML
    TextArea advert_text=new TextArea();
    @FXML
    Label order_text= new Label();
    @FXML
    Label order_details= new Label();

    @Override
    public void start(Stage primaryStage) throws IOException, NotBoundException {

        VBox root = new VBox();
        Button allow_button = new Button("Zatwierdz");
        allow_button.snapPositionX(150);
        Label text_label = new Label("Wprowadz tekst reklamy");
        Label duration_label = new Label("Wprowadz czas wyświetlania");
        TextField duration_text= new TextField();
        TextArea advert_text=new TextArea();
        Label order_text= new Label();
        Label order_details= new Label();
        Button delete_order_button = new Button("Usun zamowienie o numerze ID.");
        TextField delete_order_field= new TextField();
        Label delete_order_label= new Label();



        Registry client_registry = LocateRegistry.getRegistry("localhost", 5555);
        IClient client_stub = (IClient) client_registry.lookup("IClient");

        Registry manager_registry = LocateRegistry.getRegistry("localhost", 5556);
        IManager manager_stub = (IManager) manager_registry.lookup("IManager");

        Registry billboard_registry = LocateRegistry.getRegistry("localhost", 5557);
        IBillboard billboard_stub = (IBillboard) billboard_registry.lookup("IBillboard");

        allow_button.setOnAction(actionEvent -> {
            try {
                Duration duration = Duration.ofSeconds(Long.parseLong(duration_text.getText()));
                Order order_for_manager = client_stub.createOrder(advert_text.getText().toString(), duration, client_stub);
                int orderId = 0;
                try {
                    orderId = manager_stub.placeOrder(order_for_manager);
                    client_stub.setOrderId(order_for_manager,orderId);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
                if (orderId != 0) {
                    order_text.setText("");
                    order_details.setText("");
                    System.out.println("Przyjeto zamowienie o numerze ID: " + orderId);
                    //dodanie tekstu z zamowienia na tablice
                    billboard_stub.addAdvertisement(order_for_manager.advertText,order_for_manager.displayPeriod,order_for_manager.orderId);
                    billboard_stub.start();
                    order_details.setText("Zamówienie numer " + orderId + " przyjęte do realizacji, tekst reklamy:");
                    order_text.setText(order_for_manager.advertText);
                } else {
                    System.out.println("Nie przyjeto zamowienia");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        delete_order_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    billboard_stub.removeAdvertisement(Integer.parseInt(delete_order_field.getText()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        root.getChildren().addAll(text_label,advert_text,duration_label,duration_text,allow_button,order_details,order_text,delete_order_label,delete_order_field,delete_order_button);
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.setTitle("Client");
        primaryStage.show();

    }
}
