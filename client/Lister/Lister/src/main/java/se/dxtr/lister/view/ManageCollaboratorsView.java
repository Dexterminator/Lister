package se.dxtr.lister.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import se.dxtr.lister.R;
import se.dxtr.lister.controller.ListDetailsViewController;
import se.dxtr.lister.model.ListerModel;
import se.dxtr.lister.model.TodoList;
import se.dxtr.lister.model.User;

public class ManageCollaboratorsView {
    View view;
    public final Activity activity;
    public Button manageCollaboratorsButton;
    public int id;
    ListerModel model;

    public ManageCollaboratorsView(View view, ListerModel model, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;
        this.model = model;
        Intent intent = activity.getIntent();
        id = intent.getIntExtra(ListDetailsViewController.LIST_ID, 0);

        manageCollaboratorsButton = (Button) activity.findViewById(R.id.manage_collaborators_button);
    }

    public void update() {
        TodoList todoList = model.getTodoById(id);
        LinearLayout userContainer = (LinearLayout) activity.findViewById(R.id.user_container);
        LinearLayout dateContainer = (LinearLayout) activity.findViewById(R.id.date_added_container);
        userContainer.removeViews(2, userContainer.getChildCount() - 2);
        dateContainer.removeViews(2, dateContainer.getChildCount()-2);
        for (User collaborator: todoList.getCollaborators()) {
            TextView userElement = (TextView) View.inflate(activity.getBaseContext(), R.layout.user_element, null);
            TextView dateElement = (TextView) View.inflate(activity.getBaseContext(), R.layout.date_added_element, null);
            userElement.setText(collaborator.getName());
            dateElement.setText(collaborator.getDateCreatedString());
            userContainer.addView(userElement);
            dateContainer.addView(dateElement);
        }
    }

}
