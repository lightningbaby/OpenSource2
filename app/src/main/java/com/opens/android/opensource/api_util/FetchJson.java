package com.opens.android.opensource.api_util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ttc on 2017/3/13.
 */

public class FetchJson {
    private String url;

    public FetchJson(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getUrlBytes() throws IOException {
        URL url=new URL(getUrl());
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();

            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        url);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();

        }finally {
            connection.disconnect();
        }
    }

    public JSONObject getUrlString() throws IOException, JSONException {

        String jsonString=new String(getUrlBytes());
        JSONObject jsonBody=new JSONObject(jsonString);

        return jsonBody;
    }


}
