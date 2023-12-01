package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) throws IOException {

        URL uri = new URL("http://universities.hipolabs.com/search?country=Brazil");
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setRequestMethod("GET");
        int resCode = conn.getResponseCode();

        if (resCode == HttpURLConnection.HTTP_OK) {
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();

            String inputLine = bufferedReader.readLine();
            while ( inputLine != null) {
                stringBuffer.append(inputLine);
                inputLine = bufferedReader.readLine();
            }
            bufferedReader.close();

            List<Universidade> universidades = new ArrayList<Universidade>();
            JSONArray jsonArray = new JSONArray(stringBuffer.toString());

            for (Iterator<Object> it = jsonArray.iterator(); it.hasNext(); ) {

                JSONObject universidadeJson = (JSONObject) it.next();
                String nome = universidadeJson.getString("name");
                List<Object> uriList = universidadeJson.getJSONArray("web_pages").toList();
                String uniUri = (String) uriList.get(0);
                universidades.add(new Universidade(nome, uniUri));
            }
            System.out.println(universidades);
        } else {
            System.out.println("GET request not worked");
        }



    }
}