package se.dxtr.lister.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import se.dxtr.lister.R;
import se.dxtr.lister.ResponseReader;
import se.dxtr.lister.model.ListItem;
import se.dxtr.lister.model.TodoList;
import se.dxtr.lister.view.AddItemView;

public class AddItemViewController implements OnClickListener {
    AddItemView view;
    String content;

    public AddItemViewController(AddItemView view) {
        this.view = view;

        view.addItemButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        content = view.taskField.getText().toString();
        if (!content.equals("")){
            new AddListitemTask().execute(content);
        }
    }

    private class AddListitemTask extends AsyncTask<String, Void, String> {
        String content;

        @Override
        protected String doInBackground(String... params) {
            content = params[0];
            URL host = null;
            InputStream is = null;
            try {
                host = new URL(view.activity.getString(R.string.host) + "new_list_item/list_id=" + view.id + "&content=" + content.replace(" ", "%20"));
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
                text = "Something went wrong";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else {
                TodoList todoList = view.model.getTodoById(view.id);
                ListItem item = new ListItem(Integer.parseInt(response.replace("\n", "")), false, content);
                todoList.addListItem(item);
                todoList.setLastChange(new Date());
                view.activity.finish();
            }
        }
    }

}
