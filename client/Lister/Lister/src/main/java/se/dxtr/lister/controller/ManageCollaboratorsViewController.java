package se.dxtr.lister.controller;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import se.dxtr.lister.AddCollaboratorActivity;
import se.dxtr.lister.view.ManageCollaboratorsView;

public class ManageCollaboratorsViewController implements OnClickListener {
    ManageCollaboratorsView view;

    public ManageCollaboratorsViewController(ManageCollaboratorsView view) {
        this.view = view;

        view.manageCollaboratorsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        intent = new Intent(view.activity, AddCollaboratorActivity.class);
        view.activity.startActivity(intent);
    }

}
