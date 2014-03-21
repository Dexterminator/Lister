package se.dxtr.lister.controller;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import se.dxtr.lister.ListDetailsActivity;
import se.dxtr.lister.view.AddItemView;

public class AddItemViewController implements OnClickListener {
    AddItemView view;

    public AddItemViewController(AddItemView view) {
        this.view = view;

        view.addItemButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        intent = new Intent(view.activity, ListDetailsActivity.class);
        view.activity.startActivity(intent);
    }

}
