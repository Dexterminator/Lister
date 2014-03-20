package se.dxtr.lister.view;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import se.dxtr.lister.R;
import se.dxtr.lister.controller.ManageCollaboratorsViewController;
import se.dxtr.lister.model.ListerModel;

public class AddCollaboratorView {
    View view;
    public final Activity activity;
    public Button addCollaboratorButton;
    public EditText usernameField;
    public int id;
    public ListerModel model;


    public AddCollaboratorView(View view, ListerModel model, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;
        this.model = model;
        Intent intent = activity.getIntent();
        id = intent.getIntExtra(ManageCollaboratorsViewController.LIST_ID, 0);
        Log.d("Test addcollaborator id", String.valueOf(id));

        // Setup the rest of the view layout
        addCollaboratorButton = (Button) activity.findViewById(R.id.add_collaborator_button);
        usernameField = (EditText) activity.findViewById(R.id.added_collaborator);
    }

}
