package se.dxtr.lister.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import se.dxtr.lister.ManageCollaboratorsActivity;
import se.dxtr.lister.R;
import se.dxtr.lister.ResponseReader;
import se.dxtr.lister.view.ListDetailsView;

public class ListDetailsViewController implements OnClickListener {
    public final static String LIST_ID = "se.dxtr.lister.LIST_ID";
    ListDetailsView view;

    public ListDetailsViewController(ListDetailsView view) {
        this.view = view;
        for (CheckBox checkBox: view.listElements.keySet()) {
            checkBox.setOnClickListener(this);
        }
        view.shareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.share_button){
            Intent intent;
            intent = new Intent(view.activity, ManageCollaboratorsActivity.class);
            intent.putExtra(LIST_ID, view.id);
            view.activity.startActivity(intent);
        }else{
            String id = String.valueOf(view.listElements.get(v));
            String checked = String.valueOf(((CheckBox) v).isChecked());
            new CheckItemTask().execute(id, checked);
        }

    }
    private class CheckItemTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String checked = params[1];
            URL host = null;
            InputStream is = null;
            try {
                host = new URL(view.activity.getString(R.string.host) + "set_checked_status/id=" + id + "&checked=" + checked);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return ResponseReader.getResponse(host);
        }

        @Override
        protected void onPostExecute(String response) {
            Log.d("Checked response", response);
            //TODO Check listitem in model
        }
    }
}
