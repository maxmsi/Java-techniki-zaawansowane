package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // Locale.setDefault(new Locale("pl"));
        ResourceBundle  bundle = ResourceBundle.getBundle("inter_prop");


        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("sample.fxml"));
        loader.setResources(bundle);
        SplitPane splitPane = loader.load();
        Scene scene =  new Scene(splitPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(bundle.getString("title.application"));
        primaryStage.show();

       // primaryStage.setTitle("REST API app");
       // primaryStage.setScene(new Scene(root, 300, 275));
       // primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }

}
