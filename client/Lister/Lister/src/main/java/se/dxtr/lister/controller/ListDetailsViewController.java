package se.dxtr.lister.controller;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import se.dxtr.lister.ManageCollaboratorsActivity;
import se.dxtr.lister.view.ListDetailsView;

public class ListDetailsViewController implements OnClickListener {
    public final static String LIST_ID = "se.dxtr.lister.LIST_ID";
    ListDetailsView view;

    public ListDetailsViewController(ListDetailsView view) {
        this.view = view;

        view.shareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        intent = new Intent(view.activity, ManageCollaboratorsActivity.class);
        intent.putExtra(LIST_ID, view.id);
        view.activity.startActivity(intent);
    }

}
