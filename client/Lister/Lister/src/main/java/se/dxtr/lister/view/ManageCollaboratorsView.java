package se.dxtr.lister.view;

import se.dxtr.lister.R;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class ManageCollaboratorsView {
    View view;
    public final Activity activity;
    public Button manageCollaboratorsButton;

    public ManageCollaboratorsView(View view, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;

        // Setup the rest of the view layout
        manageCollaboratorsButton = (Button) activity.findViewById(R.id.manage_collaborators_button);
    }

}
