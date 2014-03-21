package se.dxtr.lister.view;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import se.dxtr.lister.R;
import se.dxtr.lister.model.ListerModel;

public class AddListView {
    View view;
    public final Activity activity;
    public Button addListButton;
    public ListerModel model;
    public EditText titleField;
    public EditText deadlineField;

    public AddListView(View view, ListerModel model, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;
        this.model = model;

        // Setup the rest of the view layout

        addListButton = (Button) activity.findViewById(R.id.add_list_button);
        titleField = (EditText) activity.findViewById(R.id.title_field);
        deadlineField = (EditText) activity.findViewById(R.id.deadline_field);
    }

}
