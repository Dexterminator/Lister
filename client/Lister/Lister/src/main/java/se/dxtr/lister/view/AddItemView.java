package se.dxtr.lister.view;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import se.dxtr.lister.R;
import se.dxtr.lister.controller.ListDetailsViewController;
import se.dxtr.lister.model.ListerModel;

public class AddItemView {
    View view;
    public final Activity activity;
    public Button addItemButton;
    public EditText taskField;
    public int id;
    public ListerModel model;

    public AddItemView(View view, ListerModel model, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;
        this.model = model;
        Intent intent = activity.getIntent();
        id = intent.getIntExtra(ListDetailsViewController.LIST_ID, 0);
        Log.d("Test additemview id", String.valueOf(id));

        // Setup the rest of the view layout

        addItemButton = (Button) activity.findViewById(R.id.add_item_button);
        taskField = (EditText) activity.findViewById(R.id.task_field);

    }

}
