package mbean.logic;

import java.util.HashMap;

public interface CaseServiceMBean {

   public void printTicket();
   public String getCaseName();
   public void setCaseName(String caseName);
   public int getCaseValue();
   public void setCaseValue(int caseValue);
}
