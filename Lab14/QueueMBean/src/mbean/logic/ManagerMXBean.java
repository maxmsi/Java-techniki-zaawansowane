package mbean.logic;

import java.util.List;
import java.util.Map;

public interface ManagerMXBean {

    public CaseService getCaseService();
    public void setCaseService(CaseService d) ;
    public void setNumber(int i) ;
    public int getNumber();
    public void createCaseService(int x2,String x);
    public List<CaseService> getServices();
    public void setServices(List<CaseService> services);
    public void editCaseServiceValue(String s,int newvalue);

}
