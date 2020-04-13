package app;

import app.dirManagement.DirectoryManagement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.*;

import static org.junit.Assert.assertTrue;

public class Controller {
    @FXML private ImageView image_view;
    @FXML private ChoiceBox choice_box;
    @FXML private ListView files_listview;
    @FXML private ListView dir_listview;
    @FXML private Label dir_label;
    @FXML private Label load_info;
    @FXML private TextArea file_contain;
    @FXML private Button open_button;
    @FXML private Button previous_button;
    @FXML private Button next_button;
    @FXML private Button set_new_path;
    @FXML private TextField new_dir_path;
    private ObservableList<String> items = FXCollections.observableArrayList();
    private ObservableList<String> dirs = FXCollections.observableArrayList();
    DirectoryManagement dm = new DirectoryManagement();
    static ArrayList<File> fileList = new ArrayList<>();
    static ArrayList<File> dirList = new ArrayList<>();



    private static String path = "";
    DirectoryManagement dm2= new DirectoryManagement();





    public void initialize() throws Exception {
      //  initListView();
        initFields();
        handlingButtons();
        handlingChoiceBox();


    }

    public void handlingChoiceBox(){
        choice_box.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                try {
                    initListView(choice_box.getSelectionModel().getSelectedItem().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void initFields() {
        String fileList2 = fileList.toString();
        dir_label.setText(path);

    }
    private void handlePreviousButtonAction(ActionEvent event) throws Exception {
        this.path=dm2.removeLastSlash(path);
        dir_label.setText(path);
        initListView(choice_box.getSelectionModel().getSelectedItem().toString());
    }
    private void handleOpenButtonAction(ActionEvent event) throws IOException, ExecutionException, InterruptedException {

        String fileSelectedFromListView = files_listview.getSelectionModel().getSelectedItem().toString();
        Path path2 = Paths.get(path + "/" + fileSelectedFromListView);
        final File folder = new File(String.valueOf(path2));

            if (choice_box.getSelectionModel().getSelectedItem().toString().equals("Zdjęcia")) {
                Image image = new Image(folder.toURI().toString());
                image_view.setImage(image);
                image_view.setVisible(true);
            }
            else {
                try {
                    image_view.setVisible(false);
                    showFileLines(folder);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

    //}
    private void handleNextDirButtonAction(ActionEvent event) {
        String fileSelectedFromDirView=dir_listview.getSelectionModel().getSelectedItem().toString();
        try {
            this.path=dm2.nextFolderPath(path,fileSelectedFromDirView);
            initListView(choice_box.getSelectionModel().getSelectedItem().toString());
            initFields();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void handleNewPathButtonAction(ActionEvent event) throws Exception {
        Path path = Paths.get(new_dir_path.getText());
        final File folder = new File(new_dir_path.getText());
        File file;

        if(new_dir_path.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd scieżki");
            alert.setHeaderText(null);
            alert.setContentText("Prosze podac sciężke do katalogu!");
            alert.showAndWait();
            return;
        }

        else if (!folder.isDirectory()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd scieżki");
            alert.setHeaderText(null);
            alert.setContentText("Niepoprawna scieżka która nie istnieje!");
            alert.showAndWait();
            return;
        }
        else {
            this.path=new_dir_path.getText();
            initListView(choice_box.getSelectionModel().getSelectedItem().toString());
            initFields();
        }

    }
    private void handlingButtons() {

        open_button.setOnAction(event -> {
            try {
                handleOpenButtonAction(event);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        previous_button.setOnAction(event -> {
            try {
                handlePreviousButtonAction(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        next_button.setOnAction(event -> {
            handleNextDirButtonAction(event);
        });

        set_new_path.setOnAction(event -> {
            try {
                handleNewPathButtonAction(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    public void initListView(String fileType) throws Exception {

        files_listview.getItems().clear();
        fileList.clear();
        dir_listview.getItems().clear();
        dirList.clear();
        files_listview.setItems(items);
        String [] ImageExtensions  = {"png","jpg","jpng","gif","tiff"};
        String zdjecia="Zdjęcia";

        System.out.println(fileType);
        if(fileType.equalsIgnoreCase(zdjecia)){
            if (dm2.PathMap.get(path) == null) {
                fileList = dm2.returnListFileOfDir(path);
                for (int i = 0; i < fileList.size(); i++) {
                    for (int j = 0; j < ImageExtensions.length; j++) {
                        System.out.println(ImageExtensions[j]);
                        if (dm2.getExtensionFile(fileList.get(i).getName()).equals(ImageExtensions[j])) {
                            items.add(fileList.get(i).getName());
                        }
                    }
                }

                load_info.setText("Załadowno pierwszy raz do struktury WeakHaskMap");

            } else {
                for (int i = 0; i < dm2.PathMap.get(path).size(); i++) {
                    for (String ext : ImageExtensions) {
                        System.out.println(ext);
                        if  (dm2.getExtensionFile(dm2.PathMap.get(path).get(i).getName()).equals(ext)) {
                            items.add(dm2.PathMap.get(path).get(i).getName());
                        }
                    }
                }
                load_info.setText("Pobrano ze struktury WeakHashMap");

            }
        }
        else {
            if (dm2.PathMap.get(path) == null) {
                fileList = dm2.returnListFileOfDir(path);
                for (int i = 0; i < fileList.size(); i++) {
                    items.add(fileList.get(i).getName());
                }
                load_info.setText("Załadowno pierwszy raz do struktury WeakHaskMap");
            }
            else {
                for (int i = 0; i < dm2.PathMap.get(path).size(); i++) {
                    items.add(dm2.PathMap.get(path).get(i).getName());
                }
                load_info.setText("Pobrano ze struktury WeakHashMap");

            }
        }

        dir_listview.setItems(dirs);
        dirList = dm2.returnDirectory(path);
        for (int i = 0; i < dirList.size(); i++) {
            dirs.add(dirList.get(i).getName());
        }


    }
    public void showFileLines(File file) throws InterruptedException, ExecutionException {

        file_contain.clear();

        List<String> lines=TextFileReader(file);
        for (String line : lines ) {
            file_contain.appendText(line + "\n");
        }


    }
    public List<String> TextFileReader(File file) {

            List<String> lines = new ArrayList<String>();
            String line;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));

                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
                br.close();
            } catch (IOException ex) {

            }

            return lines;
        }



}
