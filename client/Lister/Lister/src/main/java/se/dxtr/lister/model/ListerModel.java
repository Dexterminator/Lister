package se.dxtr.lister.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Observable;

/**
 * Created by Dexter on 2014-03-19.
 */
public class ListerModel extends Observable {

    public ListerModel() {
    }

    public void testRequest() {
        new TestTask().execute("1", "", "AndroidTest");
    }

    private class TestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String author = params[0];
            String deadline = params[1];
            String title = params[2];
            Log.d("Server", "***New server call...***");
            InputStream is = null;
            URL host = null;
            try {
//                host = new URL("http://192.168.1.36:8888/get_lists/id=1");
                host = new URL("http://192.168.1.36:8888/" + "new_list/" + "author=" + author
                        + "&title=" + title + "&deadline=" + deadline);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d("URL", host.toString());

            try {
                HttpURLConnection conn = (HttpURLConnection) host.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(5000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                if (response != 200) {
                    return null;
                }
                Log.d("Server", "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                Log.d("testthing", sb.toString());

                return sb.toString();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Failed";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Result", result);
        }
    }
}
