package se.dxtr.lister.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import se.dxtr.lister.R;
import se.dxtr.lister.ResponseReader;
import se.dxtr.lister.model.TodoList;
import se.dxtr.lister.model.User;
import se.dxtr.lister.view.AddCollaboratorView;

public class AddCollaboratorViewController implements OnClickListener {
    AddCollaboratorView view;
    String username;

    public AddCollaboratorViewController(AddCollaboratorView view) {
        this.view = view;

        view.addCollaboratorButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        username = view.usernameField.getText().toString();
        new AddCollaboratorTask().execute(username);
    }

    private class AddCollaboratorTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            URL host = null;
            try {
                host = new URL(view.activity.getString(R.string.host) + "share/name=" + username + "&list_id=" + view.id);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return ResponseReader.getResponse(host);
        }

        @Override
        protected void onPostExecute(String response) {
            Context context = view.activity.getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence text;
            if (response.startsWith("False")){
                text = "No such username";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else {
                TodoList todoList = view.model.getTodoById(view.id);
                User collaborator = new User(Integer.parseInt(response.replace("\n", "")), username, new Date());
                todoList.addCollaborator(collaborator);
                text = "Collaborator added!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                view.activity.finish();
            }
        }
    }


}

