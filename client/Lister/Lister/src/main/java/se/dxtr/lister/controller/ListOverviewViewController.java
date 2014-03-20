package se.dxtr.lister.controller;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import se.dxtr.lister.ListDetailsActivity;
import se.dxtr.lister.view.ListOverviewView;

public class ListOverviewViewController implements OnClickListener {
    public final static String LIST_ID = "se.dxtr.lister.LIST_ID";
    ListOverviewView view;

    public ListOverviewViewController(ListOverviewView view) {
        this.view = view;

        //TODO Somehow make listeners for each list
        for (LinearLayout element : view.overviewElements.keySet()) {
            element.setOnClickListener(this);

        }
//        view.overviewElement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = view.overviewElements.get(v);
        Log.d("list id", String.valueOf(id));
        intent = new Intent(view.activity, ListDetailsActivity.class);
        intent.putExtra(LIST_ID, id);
        view.activity.startActivity(intent);
    }

}