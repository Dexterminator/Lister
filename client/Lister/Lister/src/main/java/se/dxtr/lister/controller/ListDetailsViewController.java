package se.dxtr.lister.controller;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import se.dxtr.lister.ManageCollaboratorsActivity;
import se.dxtr.lister.view.ListDetailsView;

public class ListDetailsViewController implements OnClickListener {
    ListDetailsView view;

    public ListDetailsViewController(ListDetailsView view) {
        this.view = view;

        view.shareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        intent = new Intent(view.activity, ManageCollaboratorsActivity.class);
        view.activity.startActivity(intent);
    }

}
