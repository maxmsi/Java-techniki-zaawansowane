package app.dirManagement;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.WeakHashMap;

public class DirectoryManagement {
    static ArrayList<File> fileList = new ArrayList<>();
    static ArrayList<File> dirList = new ArrayList<>();
    private static String path = "/home/max/Dokumenty/ZPWjJ/Lab_01";
    public static String pathX;
    public static WeakHashMap<String,ArrayList<File>> PathMap = new WeakHashMap<String, ArrayList<File>>();

    public static void main(String[] args) throws Exception {

        returnListFileOfDir(path);
        System.out.println(PathMap.get(path).get(0).getName());
        System.out.println(PathMap.get(path).size());
        returnListFileOfDir("/home/max/Dokumenty/ZPWjJ");
        System.out.println(PathMap.get("/home/max/Dokumenty/ZPWjJ").get(0).getName());
        System.out.println(PathMap.get("/home/max/Dokumenty/ZPWjJ").size());
        System.out.println(PathMap.get(path).get(0).getName());
        System.out.println(PathMap.get(path).size());
    }

    public DirectoryManagement() {
    }
    public DirectoryManagement(String path) {
        this.pathX=path;
    }
    public String nextFolderPath(String path, String nextDir){
        path=path+"/"+nextDir;
        this.pathX=nextDir;
        return path;

    }
    public String removeLastSlash(String url) {
            String xxx=url.substring(0, url.lastIndexOf("/"));
            return xxx;
    }
    public String getExtensionFile(String fileName){
        char ch;
        int len;
        if(fileName==null ||
                (len = fileName.length())==0 ||
                (ch = fileName.charAt(len-1))=='/' || ch=='\\' || //in the case of a directory
                ch=='.' ) //in the case of . or ..
            return "";
        int dotInd = fileName.lastIndexOf('.'),
                sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if( dotInd<=sepInd )
            return "";
        else
            return fileName.substring(dotInd+1).toLowerCase();
    }
    public static ArrayList<File> returnDirectory(String pathname) throws Exception {
        Path path = Paths.get(pathname);
        final File folder = new File(pathname);
        File file;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                dirList.add(fileEntry);
            }
        }
        //PathMap.put(pathname,dirList);
        return dirList;
    }
    public static ArrayList<File> returnListFileOfDir(String pathname) throws Exception {

            if(PathMap.containsKey(pathname)){
                return fileList;
            }
            else {
                Path path = Paths.get(pathname);
                final File folder = new File(pathname);
                File file;
                ArrayList<File> fileList_weak_reference = new ArrayList<>();

                for (final File fileEntry : folder.listFiles()) {
                    if (fileEntry.isFile()) {
                        file = new File(pathname + "/" + fileEntry.getName());
                        fileList.add(file);
                        fileList_weak_reference.add(file);
                    }
                }

                PathMap.put(pathname, fileList_weak_reference);
            }
        return fileList;
    }
}