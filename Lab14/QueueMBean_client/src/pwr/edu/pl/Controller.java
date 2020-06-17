package pwr.edu.pl;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.management.*;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.*;

public class Controller {

    @FXML
    private ListView services_listview;
    @FXML
    private Button addservice_button;
    @FXML
    private Button edit_button;
    @FXML
    private TextField service_field;
    @FXML
    private TextField value_field;
    @FXML
    private TextField newvalue_field;
    private ObservableList<String> items = FXCollections.observableArrayList();
    int jmxPort = 8008;

    JMXServiceURL target = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + jmxPort + "/jmxrmi");
    JMXConnector connector = JMXConnectorFactory.connect(target);
    MBeanServerConnection mbs = connector.getMBeanServerConnection();
    // informacja o dostawcy wirtualnej maszyny
    ObjectName oname = new ObjectName(ManagementFactory.RUNTIME_MXBEAN_NAME);
    String vendor = (String) mbs.getAttribute(oname, "VmVendor");
    // można odczytywać oraz zapisywać dane w zdalnej aplikacji
    ManagerMXBean proxy = JMX.newMXBeanProxy(mbs, new ObjectName("mbean.logic.kbabik:name=" + "Manager"),
            ManagerMXBean.class);

    public Controller() throws IOException, MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
    }
    public void initialize() throws IOException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, MalformedObjectNameException, OpenDataException {
        handlingButtons();
        initListView(proxy.getServices());
    }

    public void initListView(List list) throws OpenDataException {
        services_listview.getItems().clear();
        for (int i = 0; i < list.size(); i++) {
            CaseService s = (CaseService) list.get(i);
            items.add(s.getCaseName()+" pr:"+s.getCaseValue());
        }
        services_listview.setItems(items);
    }

    public void handlingButtons() {
        addservice_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                proxy.createCaseService(Integer.parseInt(value_field.getText()),service_field.getText());
                try {
                    initListView(proxy.getServices());
                } catch (OpenDataException e) {
                    e.printStackTrace();
                }
            }
        });
        edit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String temp=services_listview.getSelectionModel().getSelectedItems().toString();
                proxy.editCaseServiceValue(temp.substring(1,temp.length()-6),Integer.parseInt(newvalue_field.getText()));
                try {
                    initListView(proxy.getServices());
                } catch (OpenDataException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}






