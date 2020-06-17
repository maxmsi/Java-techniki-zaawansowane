package pwr.edu.pl;

public interface CaseServiceMBean {

   public void printTicket();
   public String getCaseName();
   public void setCaseName(String caseName);
   public int getCaseValue();
   public void setCaseValue(int caseValue);
}
