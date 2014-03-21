package se.dxtr.lister.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import se.dxtr.lister.ListDetailsActivity;
import se.dxtr.lister.R;
import se.dxtr.lister.ResponseReader;
import se.dxtr.lister.model.TodoList;
import se.dxtr.lister.view.AddListView;


public class AddListViewController implements OnClickListener {
    public final static String LIST_ID = "se.dxtr.lister.LIST_ID";
    AddListView view;
    int uid;

    public AddListViewController(AddListView view) {
        this.view = view;
        uid = view.model.getLoggedInUserId();
        view.addListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String title = view.titleField.getText().toString();
        String deadline = view.deadlineField.getText().toString();
        Log.d("Test add list params", String.valueOf(uid) +  title + deadline);
        new AddListTask().execute(title, deadline);


    }

    private class AddListTask extends AsyncTask<String, Void, String> {
        String title;
        String deadline;
        @Override
        protected String doInBackground(String... params) {
            title = params[0];
            deadline = params[1];
            URL host = null;
            try {
                host = new URL(view.activity.getString(R.string.host) + "new_list/title=" + title + "&deadline=" + deadline
                + "&author=" + uid);
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
                text = "Incorrect formatting";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else {
                Date deadlineDate = null;
                try {
                    deadlineDate = new SimpleDateFormat("y-M-d").parse(deadline);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int listId = Integer.parseInt(response.replace("\n", ""));
                TodoList todoList = new TodoList(listId, title, uid, deadlineDate, new Date());
                view.model.addTodoList(todoList);
                //TODO Add logged in user as collaborator.
//                User collaborator = new User(Integer.parseInt(response.replace("\n", "")), username, new Date());
//                todoList.addCollaborator(collaborator);
                text = "List added!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent;
                intent = new Intent(view.activity, ListDetailsActivity.class);
                intent.putExtra(LIST_ID, listId);
                view.activity.startActivity(intent);
            }
        }
    }

}
