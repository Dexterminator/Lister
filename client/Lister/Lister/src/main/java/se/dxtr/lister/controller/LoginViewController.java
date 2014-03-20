package se.dxtr.lister.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import se.dxtr.lister.CreateAccountActivity;
import se.dxtr.lister.ListOverviewActivity;
import se.dxtr.lister.model.ListData;
import se.dxtr.lister.model.ListerModel;
import se.dxtr.lister.view.LoginView;
import se.dxtr.lister.R;

public class LoginViewController implements OnClickListener {
    LoginView view;
    EditText usernameField, passwordField;
    private ListerModel model;

    public LoginViewController(LoginView view, ListerModel model) {
        this.view = view;
        this.model = model;
        view.loginButton.setOnClickListener(this);
        view.createAccountLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.login_button:
                usernameField = (EditText) view.activity.findViewById(R.id.username);
                passwordField = (EditText) view.activity.findViewById(R.id.password);

                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                new LoginTask().execute(username, password);
                break;
            case R.id.create_account:
                intent = new Intent(view.activity, CreateAccountActivity.class);
                view.activity.startActivity(intent);
                break;
        }
    }

    public String validateLogin(String name, String password) {
        URL host = null;
        InputStream is = null;
        String hoster = view.activity.getString(R.string.host);
        try {
            host = new URL(view.activity.getString(R.string.host) + "login/name=" + name + "&password=" +password);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
                return "False";
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

            return sb.toString();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "False";
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            return validateLogin(name, password);
        }

        @Override
        protected void onPostExecute(String response) {
            if (response.startsWith("False")) {
                Context context = view.activity.getApplicationContext();
                CharSequence text = "Wrong username or password";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            } else {
                new FetchListTask().execute(response);
            }
            Log.d("The response: ", response);
        }
    }

    public String fetchLists(String uid) {
        URL host = null;
        InputStream is = null;
        try {
            host = new URL(view.activity.getString(R.string.host) + "get_lists/id="+uid);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
                return "False";
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

            return sb.toString();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "False";

    }

    private class FetchListTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String uid = params[0];
            return fetchLists(uid);
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent;

            Log.d("Result", result);
            Gson gson = new Gson();
            ListData listData = gson.fromJson(result, ListData.class);
            model.loadListData(listData);

            intent = new Intent(view.activity, ListOverviewActivity.class);
            view.activity.startActivity(intent);
        }
    }

}

