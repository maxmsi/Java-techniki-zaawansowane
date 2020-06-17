package mbean.logic;

import javax.management.ObjectName;
import javax.management.openmbean.*;
import java.beans.ConstructorProperties;
import java.lang.management.ManagementFactory;
import java.util.*;

public class Manager implements ManagerMXBean {

    private CaseService cs = new CaseService(0,"");
    private List<CaseService> list= new ArrayList<>();
    private int number = 0 ;

    public Manager() throws OpenDataException {
        CaseService x1= new CaseService(9,"Podatki");
        CaseService x2= new CaseService(5,"PrawoJazdy");
        CaseService x3= new CaseService(2,"Windykacje");
        CaseService x4= new CaseService(1,"SlubCywilny");
        list.add(x1);
        list.add(x2);
        list.add(x3);
        list.add(x4);
    }

    @Override
    public void editCaseServiceValue(String s,int newvalue){
        for (CaseService x:list) {
            System.out.print(x.getCaseName());
            System.out.print(s);
                if(x.getCaseName().equals(s)){
                    System.out.print("przed zmiana:"+x.getIntegerParam());
                    x.setCaseValue(newvalue);
                    System.out.print("zmiana dla:"+x);
                    System.out.print("po:"+x.getIntegerParam());
                }
        }
    }

    @Override
    public void createCaseService(int x2, String x) {
        CaseService cs = new CaseService(x2,x);
        list.add(cs);
}

    @Override
    public CaseService getCaseService() {
        CaseService cs = new CaseService(0,"");
        return cs;
    }
    @Override
    public List<CaseService> getServices(){
        return this.list;
    }

    @Override
    public void setServices(List<CaseService> services) {
        this.list=services;
    }

    @Override
    public void setCaseService(CaseService d) {
        this.cs = d;
    }

    @Override
    public void setNumber(int i) {
        this.number = i;
    }

    @Override
    public int getNumber() {
        return this.number;
    }
    /*
     * Należy uruchomić z opcjami: -Dcom.sun.management.jmxremote
     * -Dcom.sun.management.jmxremote.port=8008
     * -Dcom.sun.management.jmxremote.authenticate=false
     * -Dcom.sun.management.jmxremote.ssl=false
     */
    public static void main(String[] args) throws OpenDataException {
        Manager impl = new Manager();

        try {
            ManagementFactory.getPlatformMBeanServer().registerMBean(impl,
                    new ObjectName("mbean.logic.kbabik:name=" + "Manager"));
            Thread.currentThread().join();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

}