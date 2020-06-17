package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import mbean.logic.CaseService;
import mbean.logic.ManagerMXBean;

import javax.management.*;
import javax.management.openmbean.OpenDataException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class QueController {

    @FXML
    public ListView sprawy_listview;
    @FXML
    public Button generuj_button;
    @FXML
    public Button refresh_button;
    @FXML
    public TextArea queue;
    @FXML
    public TableView cases_tableview;
    public int ticketNo=1;

    private ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<ObservableList> data;
    private Map<String,Integer> tickets=new TreeMap<>();
    private List<CaseService> listservices=new ArrayList<>();

    int jmxPort = 8008;
    // utworzenie połšczenia
    // port powinien zgadzać się z portem ustawionym w opcjach,
    // z jakimi uruchomiono Menager
    JMXServiceURL target = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + jmxPort + "/jmxrmi");
    JMXConnector connector = JMXConnectorFactory.connect(target);
    MBeanServerConnection mbs = connector.getMBeanServerConnection();
    // informacja o dostawcy wirtualnej maszyny
    ObjectName oname = new ObjectName(ManagementFactory.RUNTIME_MXBEAN_NAME);
    String vendor = (String) mbs.getAttribute(oname, "VmVendor");
    // można odczytywać oraz zapisywać dane w zdalnej aplikacji
    ManagerMXBean proxy = JMX.newMXBeanProxy(mbs, new ObjectName("mbean.logic.kbabik:name=" + "Manager"),
            ManagerMXBean.class);

    public QueController() throws IOException, MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
    }

    public void initialize() throws IOException, MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, OpenDataException, InterruptedException {
        initListView(proxy.getServices());
        handlingButtons();
    }

    public void initListView(List list) throws OpenDataException {
        sprawy_listview.getItems().clear();
        for (int i = 0; i < list.size(); i++) {
            CaseService s = (CaseService) list.get(i);
            listservices.add(i,s);
            items.add(s.getCaseName());
        }
        sprawy_listview.setItems(items);
    }
    public void handlingButtons() {
        generuj_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String temp= sprawy_listview.getSelectionModel().getSelectedItems().toString();
                for(int i=0;i<items.size();i++) {
                    if(temp.substring(1,temp.length()-1).equals(listservices.get(i).getCaseName())) {
                        tickets.put(temp.substring(1,3)+ticketNo,listservices.get(i).getCaseValue());
                                ticketNo++;
                                System.out.print("dodano tiket");
                                queue.setText("");
                                refreshQueue();

                    }
                }
            }
        });
        refresh_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {

                    initListView(proxy.getServices());
                    queue.setText("");
                    refreshQueue();
                } catch (OpenDataException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void refreshQueue(){

        String actual=queue.getText()+" <-";
        for (Map.Entry<String, Integer> entry : tickets.entrySet()) {
            actual+=entry.getKey()+" <-";
        }
        queue.setText(actual);
    }

}

