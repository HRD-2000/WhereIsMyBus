package com.hrd.whereismybus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by manthan on 10-Jun-18.
 */

public class json {
    String result;

    public String insert(String s) {
        try {
            URL url = new URL(s);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }
}

