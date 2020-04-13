package pl.edu.pwr.appmodule;

import static pl.edu.pwr.libmod.genereteMD5.checkImprint;
import static pl.edu.pwr.libmod.genereteMD5.makeImprint;

public class TestClass {
    public static void main(String[] args) throws Exception {

        String path="/home/max/Dokumenty/ZPWjJ/Lab_01";
        String fileName1="md5_imprint";
        String fileName2="md5_imprint_to_compare";
        //makeImprint(path,fileName1);
        checkImprint(path,fileName2);
    }
}
