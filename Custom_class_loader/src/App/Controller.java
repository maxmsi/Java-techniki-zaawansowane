package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;


import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Controller {


    @FXML ComboBox<String> classlist;
    @FXML TextArea getInfoText;
    @FXML Button loadclassButton;
    @FXML Button unloadclassButton;
    @FXML Button startProcessingButton;
    @FXML TextArea resultTextArea;
    @FXML TextArea toprocessText;



    private List<String> foundClassNames2 = new ArrayList<>();
    OwnClassLoader loaderClass = new OwnClassLoader("/home/max/IdeaProjects/Lab_04/toLoadClasses");

    Class clazz;
    Object obj;

    public void initialize() throws Exception {
        getClasses();
        handlingButtons();
        for (int i = 0; i < foundClassNames2.size(); i++) {
            classlist.getItems().addAll(foundClassNames2.get(i));
        }
    }
    public void getClasses() throws Exception {
        foundClassNames2=loaderClass.returnListFileOfDir();

    }
    private void handleLoadClassButton(ActionEvent event) throws IOException, ExecutionException, InterruptedException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        this.clazz = loaderClass.loadClass(classlist.getSelectionModel().getSelectedItem());
        this.obj = clazz.newInstance();

        Method getInfo=clazz.getMethod("getInfo");
        Method md=clazz.getMethod("getInfo");

        getInfoText.setText((String) md.invoke(obj));
    }
    private void handleStartProcessingButton(ActionEvent e) throws NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException {
        resultTextArea.setText("");
        Field StatusClass=clazz.getDeclaredField("state");

        Method submitTask=clazz.getDeclaredMethod("submitTask",String.class);
        Method getResult=clazz.getDeclaredMethod("getResult");

        submitTask.invoke(obj,toprocessText.getText());
        resultTextArea.setText((String) getResult.invoke(obj));

    }

    private void handleUnloadClassButton(ActionEvent event) throws Throwable {
        obj=null;
        clazz=null;
        loaderClass=null;
        System.gc();

        getInfoText.setText("Class unloaded");
    }

    public void handlingButtons(){
        loadclassButton.setOnAction(event -> {
            try {
                handleLoadClassButton(event);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        startProcessingButton.setOnAction(event -> {
            try {
                handleStartProcessingButton(event);
            }catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        unloadclassButton.setOnAction(event -> {
            try {
                handleUnloadClassButton(event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });

    }

}
