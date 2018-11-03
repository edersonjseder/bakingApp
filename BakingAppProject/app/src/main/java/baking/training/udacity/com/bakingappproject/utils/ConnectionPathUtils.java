package baking.training.udacity.com.bakingappproject.utils;


import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import baking.training.udacity.com.bakingappproject.BuildConfig;

public class ConnectionPathUtils {
    private static final String TAG = ConnectionPathUtils.class.getSimpleName();

    // URL where JSON file is, saved in gradle.properties
    private static final String PATH = BuildConfig.URL_JSON;

    /**
     * This method builds the URL to retrieve the JSON to be saved in the Java objects
     *
     * @return
     */
    public static URL buildJsonUrl() {

        URL builtUri = null;
        try {

            builtUri = new URL(Uri.parse(PATH).buildUpon().build().toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + builtUri.toString());

        return builtUri;

    }

    /**
     * This method does the query on the API to retrieve the JSON result
     *
     * @param generatedUrl The given URL built
     * @return The Json result got from the API Url
     * @throws IOException
     */
    public static String doQuery(URL generatedUrl) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        StringBuilder result = new StringBuilder();

        try{

            // Gets the URL with the param cityName, and with it concatenated on the url gets the JSON weather info
            URL url = generatedUrl;

            // Open the connection with the URL here
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {

                result.append(scanner.next());

            }

        } catch (MalformedURLException e) {
            Log.e(TAG, "doQuery() inside catch block - " + e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            Log.e(TAG, "doQuery() inside catch block - " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            Log.e(TAG, "doQuery() inside catch block - " + e.getMessage());
            e.printStackTrace();

        } finally {
            Log.i(TAG, "doQuery() inside finally block");
            if (connection != null){
                connection.disconnect();
            }
        }

        Log.i(TAG, "Result: (" + result + ")");

        return result.toString();
    }

}
