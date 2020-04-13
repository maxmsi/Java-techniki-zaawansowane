package sample;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import org.json.JSONException;
import sample.model.MovieObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    TestCllas httpResponse=new TestCllas();
    ArrayList<MovieObject> mv=new ArrayList<>();
    @FXML private TableView<MovieObject> tableMovieList;
    @FXML private TableColumn<MovieObject, String> columnTitle;
    @FXML private TableColumn<MovieObject, String> columnYear;
    @FXML private TableColumn<MovieObject, String> columnDirector;
    @FXML private TableColumn<MovieObject, String> columnRelease;
    @FXML private TableColumn<MovieObject, String> columnGrade;
    @FXML private TableColumn<MovieObject, String> columnGenre;
    @FXML private TableColumn<MovieObject, String> columnLanguage;
    @FXML private Button search_button;
    @FXML private TextField movie_title;
    @FXML private TextField movie_release;
    @FXML private Label first_label;
    @FXML private Label second_label;
    @FXML private MenuItem set_polish;
    @FXML private MenuItem set_english;
    @FXML private MenuItem showid;
    @FXML private MenuItem aboutprogram;
    @FXML private Menu lang;
    @FXML private Menu about;
    @FXML private Menu help;
   // ResourceBundle resourceBundle = ResourceBundle.getBundle("language");
   // Locale locale;

    ResourceBundle  bundle = ResourceBundle.getBundle("inter_prop");
    Locale locale;

    private void handleButtonAction(ActionEvent event) throws IOException, JSONException {
           CharSequence title=movie_title.getCharacters();
           CharSequence release= movie_release.getCharacters();
           String prepareTitle = "";
           String prepareRelease = release.toString();
           for(int i=0;i<title.length();i++){
                if(title.charAt(i)==' '){
                    prepareTitle=prepareTitle+"+";
                }
                else
                { prepareTitle=prepareTitle+title.charAt(i);
                }

            }

           loadData(prepareTitle,prepareRelease);

            System.out.println(prepareTitle);
            System.out.println(prepareRelease);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        search_button.setOnAction(event -> {
            try {
                handleButtonAction(event);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        set_english.setOnAction(actionEvent -> {
            try {
                handleSetEnlgish("eng");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        set_polish.setOnAction(actionEvent -> {
            try{
                handleSetPolish("pl");
            }catch (IOException e){
            e.printStackTrace();
            }
        });

        about.setOnAction(actionEvent -> {
            aboutMe(bundle);
        });

        aboutprogram.setOnAction(actionEvent -> {
            aboutProgram(bundle);
        });

        initTable();
        try {
            loadData("","");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void aboutMe(ResourceBundle bundle) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(bundle.getString("aboutinfo"));
        a.showAndWait();
        return;

    }

    public void aboutProgram(ResourceBundle bundle) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(bundle.getString("aboutprograminfo"));
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        a.showAndWait();
        return;

    }

    private void handleSetEnlgish(String lang) throws IOException {
        //Locale.setDefault(new Locale("eng"));
        locale= new Locale("eng");
        bundle = ResourceBundle.getBundle("inter_prop",locale);
        refreshLanguage(bundle);

    }

        private void handleSetPolish(String lang) throws IOException {
            //Locale.setDefault(new Locale("pl"));
            locale= new Locale("pl");
            bundle = ResourceBundle.getBundle("inter_prop",locale);
            refreshLanguage(bundle);

        }


    private void refreshLanguage(ResourceBundle bundle){
        columnTitle.setText(bundle.getString("titlebundle"));
        columnDirector.setText(bundle.getString("director"));
        columnLanguage.setText(bundle.getString("languagebundle"));
        columnGenre.setText(bundle.getString("genre"));
        columnGrade.setText(bundle.getString("grade"));
        columnRelease.setText(bundle.getString("Wydanie"));

        search_button.setText(bundle.getString("search_button"));
        movie_title.setText(bundle.getString("first_textfield"));
        movie_release.setText(bundle.getString("second_textfield"));
        first_label.setText(bundle.getString("firstlabel"));
        second_label.setText(bundle.getString("secondlabelyear"));
        lang.setText(bundle.getString("menulanguage"));
        about.setText(bundle.getString("aboutme"));
        help.setText(bundle.getString("helpkey"));
        showid.setText(bundle.getString("show"));
        aboutprogram.setText(bundle.getString("proginf"));


    }
    private void initTable(){
            initColumns();
        }
        private void initColumns(){
            columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            //columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
            columnDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
            columnRelease.setCellValueFactory(new PropertyValueFactory<>("release"));
            columnGrade.setCellValueFactory(new PropertyValueFactory<>("rated"));
            columnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            columnLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));
            editableCols();
        }
        private void editableCols(){

        columnTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        columnTitle.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTitle(e.getNewValue());
        });

        //   columnYear.setCellFactory(TextFieldTableCell.forTableColumn());
         //   columnYear.setOnEditCommit(e->{
          //      e.getTableView().getItems().get(e.getTablePosition().getRow()).setYear(e.getNewValue());
         //   });

            columnDirector.setCellFactory(TextFieldTableCell.forTableColumn());
            columnDirector.setOnEditCommit(e->{
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setDirector(e.getNewValue());
            });

           columnRelease.setCellFactory(TextFieldTableCell.forTableColumn());
            columnRelease.setOnEditCommit(e->{
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setRelease(e.getNewValue());
            });

            columnGrade.setCellFactory(TextFieldTableCell.forTableColumn());
            columnGrade.setOnEditCommit(e->{
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setRated(e.getNewValue());
            });

            columnGenre.setCellFactory(TextFieldTableCell.forTableColumn());
            columnGenre.setOnEditCommit(e->{
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setGenre(e.getNewValue());
            });
            columnLanguage.setCellFactory(TextFieldTableCell.forTableColumn());
            columnLanguage.setOnEditCommit(e->{
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setLanguage(e.getNewValue());
            });

            tableMovieList.setEditable(true);
    }
        private void loadData(String title,String release) throws IOException, JSONException {

        ObservableList<MovieObject> data_table = FXCollections.observableArrayList();
        mv=TestCllas.getRequest(title,release);

        for (int i = 0; i<mv.size() ; i++){
            data_table.add(mv.get(i));
           // data_table.add(new MovieObject(String.valueOf(i),"Year"+i,"type"+i,"director"+i,
               //     "release"+i,"rated"+i,"genre"+i,"language"+i));
        }
        tableMovieList.setItems(data_table);
    }

}
