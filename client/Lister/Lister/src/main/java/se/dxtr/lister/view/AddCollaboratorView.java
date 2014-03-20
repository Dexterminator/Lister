package se.dxtr.lister.view;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import se.dxtr.lister.R;
import se.dxtr.lister.controller.ManageCollaboratorsViewController;

public class AddCollaboratorView {
    View view;
    public final Activity activity;
    public Button addCollaboratorButton;

    public AddCollaboratorView(View view, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;
        Intent intent = activity.getIntent();
        int id = intent.getIntExtra(ManageCollaboratorsViewController.LIST_ID, 0);
        Log.d("Test addcollaborator id", String.valueOf(id));

        // Setup the rest of the view layout
        addCollaboratorButton = (Button) activity.findViewById(R.id.add_collaborator_button);
    }

}
