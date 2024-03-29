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
import se.dxtr.lister.model.User;
import se.dxtr.lister.view.AddListView;


public class AddListViewController implements OnClickListener {
    public final static String LIST_ID = "se.dxtr.lister.LIST_ID";
    AddListView view;
    User user;

    public AddListViewController(AddListView view) {
        this.view = view;
        user = view.model.getLoggedInUser();
        view.addListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String title = view.titleField.getText().toString();
        String deadline = view.deadlineField.getText().toString();
        Log.d("Test add list params", String.valueOf(user.getId()) +  title + deadline);
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
                host = new URL(view.activity.getString(R.string.host) + "new_list/title=" + title.replace(" ", "%20") + "&deadline=" + deadline
                + "&author=" + user.getId());
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
                TodoList todoList = new TodoList(listId, title, user.getId(), deadlineDate, new Date());
                view.model.addTodoList(todoList);
                todoList.addCollaborator(view.model.getLoggedInUser());
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
