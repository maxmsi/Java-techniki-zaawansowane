package App;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OwnClassLoader extends ClassLoader{

    private static List<String> foundClassNames = new ArrayList<>();
    private static String loadFromDirPath;

    public OwnClassLoader(String dirPath) {
        loadFromDirPath = dirPath;
       // if (!loadFromDirPath.endsWith("/"))
        //  loadFromDirPath = loadFromDirPath + "/";
        System.out.println(loadFromDirPath);
    }

    public static List<String> returnListFileOfDir() throws Exception {

            Path path = Paths.get(loadFromDirPath+"/processing");
            final File folder = new File(loadFromDirPath+"/processing");
            File file;

            for (final File fileEntry : folder.listFiles()) {

                    file = new File(loadFromDirPath +"/processing/" + fileEntry.getName());
                    if(getExtensionFile(fileEntry.getName()).equals("class")){
                        foundClassNames.add("processing."+fileEntry.getName().substring(0,fileEntry.getName().lastIndexOf('.')));
                    }
            }
        return foundClassNames;
    }
    public static String getExtensionFile(String fileName){
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


    public void loadClassNames()
    {
        try {
            String finalDirPath = loadFromDirPath;
            Files.walk(Paths.get(loadFromDirPath)).filter(Files::isRegularFile).forEach((f) -> {
                String absolutePath = f.toAbsolutePath().toString();
                if (absolutePath.endsWith(".class")) {
                    foundClassNames.add(absolutePath.replace(finalDirPath, "").replace(".class","" ));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  List<String> getClassNamesInDirectory()
    {
        return foundClassNames;
    }

    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        Path classPath = Paths.get(loadFromDirPath+ "/" + name.replace('.', File.separatorChar) + ".class");
        if (Files.exists(classPath)){
            try {
                byte[] byteCode = Files.readAllBytes(classPath);
                return this.defineClass(name, byteCode, 0, byteCode.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(name, e);
            }
        } else {
            throw new ClassNotFoundException(name);
        }
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("del");
    }

}
