package pwr.edu.pl;

import javax.management.openmbean.*;
import java.beans.ConstructorProperties;

public class CaseService implements CaseServiceMBean, CompositeDataView {
    private String caseName;
    private Integer caseValue;

    // zaimplementować CompositeDataView
    public void method() {
    }

    public static CaseService from(CompositeData cd) {
        return new CaseService((Integer) cd.get("integerParam"), (String) cd.get("stringParam"));

    }
    @ConstructorProperties({ "integerParam", "stringParam" })
    public CaseService(int i, String s) {
        this.caseValue= i;
        this.caseName= s;
    }

    // Implementacja metody z interfejsu CompositeDataView
//    @Override
//    public CompositeData toCompositeData(CompositeType ct) {
//        try {
//            CompositeData cd = new CompositeDataSupport(ct, new String[] { "caseValue", "caseName" },
//                    new Object[] { caseName, caseValue });
//            assert ct.isValue(cd);
//            return cd;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
    @Override
    public CompositeData toCompositeData(CompositeType ct) {
        CompositeDataSupport support = null;
        try {
            support = new CompositeDataSupport(
                    new CompositeType("CaseService data type", "dta type for CaseService information",
                            new String[] { "integerParam", "stringParam" }, new String[] { "intp desc", "strp desc" },
                            new OpenType[] { SimpleType.INTEGER, SimpleType.STRING
                    }),
                    new String[] { "integerParam", "stringParam" },
                    new Object[] { getIntegerParam(), getStringParam() });
        } catch (OpenDataException e) { // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return support;
    }
//    @Override
//    public void printTicket() {
//
//    }

    @Override
    public void printTicket() {

    }

    @Override
    public String getCaseName() {
        return this.caseName;
    }

    @Override
    public void setCaseName(String caseName) {
        this.caseName=caseName;
    }

    @Override
    public int getCaseValue() {
        return this.caseValue;
    }

    @Override
    public void setCaseValue(int caseValue) {
        this.caseValue=caseValue;
    }

    public String getStringParam() {
        return caseName;
    }


    public int getIntegerParam() {
        return caseValue;
    }
    }

