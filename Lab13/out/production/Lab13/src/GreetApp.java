
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GreetApp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    String options[] = { "Dark", "White",};
    @FXML ComboBox themeBox;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        String fxmlDocPath = "src/GreetAPp.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        VBox root = (VBox) loader.load(fxmlStream);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application_white.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("A Greeting generator app without any Controller");
        stage.show();

    }
}
