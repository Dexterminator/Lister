package se.dxtr.lister.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;

import se.dxtr.lister.AddListActivity;
import se.dxtr.lister.ListDetailsActivity;
import se.dxtr.lister.R;
import se.dxtr.lister.ResponseReader;
import se.dxtr.lister.model.ListData;
import se.dxtr.lister.view.ListOverviewView;

public class ListOverviewViewController implements OnClickListener {
    public final static String LIST_ID = "se.dxtr.lister.LIST_ID";
    ListOverviewView view;

    public ListOverviewViewController(ListOverviewView view) {
        this.view = view;
    }

    public void update() {
        for (LinearLayout element : view.overviewTitleElements.keySet()) {
            element.setOnClickListener(this);

        }
        view.newListButton.setOnClickListener(this);
        view.syncButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.new_list_button) {
            intent = new Intent(view.activity, AddListActivity.class);
            view.activity.startActivity(intent);
        } else if (v.getId() == R.id.sync_button) {
            Log.d("Server", "***New server call***");
            new FetchListTask().execute(String.valueOf(view.model.getLoggedInUser().getId()));
        } else {
            int id = view.overviewTitleElements.get(v);
            Log.d("list id", String.valueOf(id));
            intent = new Intent(view.activity, ListDetailsActivity.class);
            intent.putExtra(LIST_ID, id);
            view.activity.startActivity(intent);
        }
    }

    private class FetchListTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String uid = params[0];
            URL host = null;
            try {
                host = new URL(view.activity.getString(R.string.host) + "get_lists/id="+uid);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return ResponseReader.getResponse(host);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Result", result);
            Gson gson = new Gson();
            ListData listData = gson.fromJson(result, ListData.class);
            view.model.loadListData(listData);
            view.update();
            update();
        }
    }

}