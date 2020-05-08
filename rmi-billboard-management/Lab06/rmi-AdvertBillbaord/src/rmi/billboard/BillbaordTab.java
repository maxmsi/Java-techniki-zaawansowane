package rmi.billboard;

import billboards.AdvertToShow;

import billboards.IBillboard;
import billboards.IClient;

import billboards.IManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BillbaordTab extends Application {

    VBox root = new VBox();
    //Button allow_button = new Button("Start wyświetlania");
    //Label text_label = new Label("Wyświetlam reklama o numerze ID:");


    private int count = 0;
    private final Text text = new Text(Integer.toString(count));

    List<AdvertToShow> advToShow= null;

    public void updateAdvert(String s){
        text.setText(s);
    }
    @Override
    public void start(Stage primaryStage) throws IOException, NotBoundException {
        Registry client_registry = LocateRegistry.getRegistry("localhost", 5555);
        IClient client_stub = (IClient) client_registry.lookup("IClient");

        Registry manager_registry = LocateRegistry.getRegistry("localhost", 5556);
        IManager manager_stub = (IManager) manager_registry.lookup("IManager");

        Registry billboard_registry = LocateRegistry.getRegistry("localhost", 5557);
        IBillboard billboard_stub = (IBillboard) billboard_registry.lookup("IBillboard");



        Duration advertDuration;
        String emptyString="";
        advToShow = billboard_stub.getAdvertList();
        final AtomicInteger index = new AtomicInteger( 0 ) ;
        final AtomicInteger[] lastindex = {new AtomicInteger(advToShow.size() - 1)};

        Long interval = billboard_stub.getDisplayInterval().getSeconds();
        Timeline tl = new Timeline(new KeyFrame(javafx.util.Duration.seconds(interval), e -> {
                updateAdvert(emptyString);
                Long duration=null;
                if(advToShow.size()>0) {
                    duration=advToShow.get(index.get()).getAdvertDuration().getSeconds();
                }
                if(duration>=interval) {
                    //wyswietlenie reklamy.
                    updateAdvert(advToShow.get(index.get()).getAdvertText());
                    //Zmniejszenie czasu wyswietlania reklamu o ustalony przez Menagera DisplayPeriod jednej reklamy.
                    advToShow.get(index.get()).setAdvertDuration(advToShow.get(index.get()).getAdvertDuration().minusSeconds(interval));
                    System.out.print(advToShow.get(index.get()).getAdvertDuration().getSeconds());
                }

                if(index.get()== lastindex[0].get()) {
                    index.set(0);
                }
                else {
                    index.incrementAndGet();
                }
            lastindex[0].set(advToShow.size()-1);
            lastindex[0] = new AtomicInteger( advToShow.size() -1) ;
        }));
            tl.setCycleCount(Timeline.INDEFINITE);
            tl.play();

        root.getChildren().addAll(text);
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.setTitle("BillbaordTab");
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
