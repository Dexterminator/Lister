package se.dxtr.lister.controller;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import se.dxtr.lister.ListDetailsActivity;
import se.dxtr.lister.view.ListOverviewView;

public class ListOverviewViewController implements OnClickListener {
    ListOverviewView view;

    public ListOverviewViewController(ListOverviewView view) {
        this.view = view;

        //TODO Somehow make listeners for each list
//        view.overviewElement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        intent = new Intent(view.activity, ListDetailsActivity.class);
        view.activity.startActivity(intent);
    }

}