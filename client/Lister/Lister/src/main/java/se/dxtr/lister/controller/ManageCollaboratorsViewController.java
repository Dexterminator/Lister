package se.dxtr.lister.controller;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import se.dxtr.lister.AddCollaboratorActivity;
import se.dxtr.lister.view.ManageCollaboratorsView;

public class ManageCollaboratorsViewController implements OnClickListener {
    public final static String LIST_ID = "se.dxtr.lister.LIST_ID";
    ManageCollaboratorsView view;

    public ManageCollaboratorsViewController(ManageCollaboratorsView view) {
        this.view = view;

        view.manageCollaboratorsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = view.id;
        intent = new Intent(view.activity, AddCollaboratorActivity.class);
        intent.putExtra(LIST_ID, id);
        view.activity.startActivity(intent);
    }

}
