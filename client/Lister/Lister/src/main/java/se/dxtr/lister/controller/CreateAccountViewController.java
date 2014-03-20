package se.dxtr.lister.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import se.dxtr.lister.R;
import se.dxtr.lister.ResponseReader;
import se.dxtr.lister.view.CreateAccountView;

public class CreateAccountViewController implements OnClickListener {
    CreateAccountView view;

    public CreateAccountViewController(CreateAccountView view) {
        this.view = view;

        view.createAccountButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = ((EditText) view.activity.findViewById(R.id.created_username))
                .getText().toString();
        String password = ((EditText) view.activity.findViewById(R.id.created_password))
                .getText().toString();

        if (username.length() < 3 || password.length() < 3) {
            Context context = view.activity.getApplicationContext();
            CharSequence text = "Password or username is too short";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else {
            new createAccountTask().execute(username, password);
        }

    }

    private String createAccount(String username, String password) {
        URL host = null;
        try {
            host = new URL(view.activity.getString(R.string.host) + "create_account/name="
                    + username + "&password=" + password);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseReader.getResponse(host);
    }

    private class createAccountTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            return createAccount(username, password);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Create account response", result);
            CharSequence text;
            Context context = view.activity.getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            if (result.startsWith("False")) {
                text = "Illegal characters in username or password";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else{
                text = "Account created!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                view.activity.finish();
            }
        }
    }

}
