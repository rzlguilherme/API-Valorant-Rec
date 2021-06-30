package com.example.apivalorant;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String VALORANT_URL = "https://valorant-api.com/v1/agents/";

    static String searchCharacterInfo(String queryString){
        HttpURLConnection urlconnection = null;
        BufferedReader reader = null;
        String characterJSONString = null;
        try{

            String URL = VALORANT_URL + queryString;

            URL requestURL = new URL(URL);

            urlconnection = (HttpURLConnection) requestURL.openConnection();
            urlconnection.setRequestMethod("GET");
            urlconnection.connect();

            InputStream inputStream = urlconnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String row;

            while((row = reader.readLine()) != null){
                builder.append(row);
                builder.append("\n");
            }
            if(builder.length() == 0){
                return null;
            }
            characterJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(urlconnection != null){
                urlconnection.disconnect();
            }
            if(reader != null){
                try{
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, characterJSONString);
        return  characterJSONString;
    }
}
