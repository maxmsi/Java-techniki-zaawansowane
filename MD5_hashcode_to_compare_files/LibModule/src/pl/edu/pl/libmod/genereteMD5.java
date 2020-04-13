package pl.edu.pwr.libmod;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class genereteMD5 {

    public static byte[] createChecksum(String filename) throws Exception {
        InputStream fis =  new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }
    public static String getMD5Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";

        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
    // Driver code
    public static void main(String args[]) throws Exception {

        String path="/home/max/Dokumenty/ZPWjJ/Lab_01";
        String fileName1="md5_imprint";
        String fileName2="md5_imprint_to_compare";
       //makeImprint(path,fileName1);
        checkImprint(path,fileName2);


    }


    private static String getFileChecksum(MessageDigest digest, File file) throws IOException
    {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }

    public static void makeImprint(String pathname,String setFileName) throws Exception {
        // get path object pointing to the directory we wish to monitor
        //add pathname as a parameter


        Path path = Paths.get(pathname);
        BufferedWriter writer = new BufferedWriter(new FileWriter(setFileName));
        final File folder = new File(pathname);
        File file;
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");

        for (final File fileEntry : folder.listFiles()) {
            if(fileEntry.isFile()){
                file=new File(pathname+"/"+fileEntry.getName());
                System.out.print(getFileChecksum(md5Digest,file)+"::"+fileEntry.getName().toString()+"\n");
                writer.write(getFileChecksum(md5Digest,file)+"::"+fileEntry.getName().toString()+"\n");
            }
        }
        writer.close();
        }


        public static void checkImprint(String pathname,String filename) throws Exception {
            makeImprint(pathname,filename);
            BufferedReader reader1 = new BufferedReader(new FileReader("/home/max/IdeaProjects/Lab01_02/md5_imprint"));
            BufferedReader reader2 = new BufferedReader(new FileReader("/home/max/IdeaProjects/Lab01_02/md5_imprint_to_compare"));

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            boolean areEqual = true;
            int lineNum = 1;

            while (line1 != null || line2 != null)
            {
                if(line1 == null || line2 == null)
                {
                    areEqual = false;
                    break;
                }
                else if(! line1.equalsIgnoreCase(line2))
                {
                    areEqual = false;
                    break;
                }

                line1 = reader1.readLine();
                line2 = reader2.readLine();

                lineNum++;
            }

            if(areEqual)
            {
                System.out.println("Two files have same content.");
            }
            else
            {
                System.out.println("Two files have different content. They differ at line "+lineNum);
                System.out.println("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
            }

            reader1.close();
            reader2.close();
        }
    }


