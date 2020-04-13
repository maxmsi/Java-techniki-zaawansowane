package sample;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import sample.model.MovieObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;




public class TestCllas {

    private static HttpURLConnection connection;

    private static String APIkey="68WIugO4a0zJK1cfvkae2o7Cd3aWF2z2TdnZw3ud";
    private static String inline;
    static MovieObject mv;

    static JSONParser parse =new JSONParser();
    static ArrayList<MovieObject> movieList=new ArrayList<>();

    public static ArrayList<MovieObject>  getRequest(String titleField,String releaseField) throws IOException, JSONException {

        String x="http://www.omdbapi.com/?apikey=da68343c&t="+titleField+"&y="+releaseField;
        URL url = new URL(x);

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();
        int counter=0;

        int responseCode = connection.getResponseCode();
        if(responseCode!=200)
            throw new RuntimeException("HttpResponseCode:"+responseCode);
        else {
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()){

                    inline = sc.nextLine();
                    JSONObject myObject = new JSONObject(inline);
                    String title= (String) myObject.get("Title");
                    String year = (String) myObject.get("Year");
                    String type = (String) myObject.get("Type");
                    String rated = (String) myObject.get("Rated");
                    String release = (String) myObject.get("Released");
                    String genre = (String) myObject.get("Genre");
                    String director = (String) myObject.get("Director");
                    String language= (String) myObject.get("Language");
                    movieList.add(new MovieObject(title,year,type,director,release,rated,genre,language));
                    System.out.println(myObject);
                   // movieList.add(mv);

                }
            }
           return movieList;
        }


    public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException, JSONException {

    }

}
