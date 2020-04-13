package app.tests;

import org.junit.Test;
import app.dirManagement.DirectoryManagement;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class DirectoryManagementTests {

    static ArrayList<File> fileList = new ArrayList<>();
    private static String path = "/home/max/Dokumenty/ZPWjJ/Lab_01";
   // DirectoryManagement dm=new DirectoryManagement();





    @Test
    public void shouldSaySizeIs3() throws Exception {
       // fileList= dm.returnListFileOfDir(path);
        assertTrue(3==fileList.size());
    }

}
