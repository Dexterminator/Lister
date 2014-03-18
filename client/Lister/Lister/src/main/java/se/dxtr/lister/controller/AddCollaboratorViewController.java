package se.dxtr.lister.controller;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import se.dxtr.lister.ManageCollaboratorsActivity;
import se.dxtr.lister.view.AddCollaboratorView;
import se.dxtr.lister.view.ManageCollaboratorsView;

public class AddCollaboratorViewController implements OnClickListener {
    AddCollaboratorView view;

    public AddCollaboratorViewController(AddCollaboratorView view) {
        this.view = view;

        view.addCollaboratorButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        view.activity.finish();
    }

}
