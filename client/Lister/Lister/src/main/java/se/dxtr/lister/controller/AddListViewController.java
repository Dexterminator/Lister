package se.dxtr.lister.controller;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import se.dxtr.lister.ListDetailsActivity;
import se.dxtr.lister.view.AddListView;


public class AddListViewController implements OnClickListener {
    AddListView view;

    public AddListViewController(AddListView view) {
        this.view = view;

        view.addListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        intent = new Intent(view.activity, ListDetailsActivity.class);
        view.activity.startActivity(intent);
    }

}
