package app.dirManagement;

public class directoryModel {
    private static String fileName;




    public directoryModel(String fileName){
        this.fileName=fileName;
    }

    public static String getFileName() {
        return fileName;
    }

    public static void setFileName(String fileName) {
        directoryModel.fileName = fileName;
    }
}
