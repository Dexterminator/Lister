package se.dxtr.lister.view;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import se.dxtr.lister.R;
import se.dxtr.lister.controller.ListOverviewViewController;
import se.dxtr.lister.model.ListItem;
import se.dxtr.lister.model.ListerModel;
import se.dxtr.lister.model.TodoList;

public class ListDetailsView {
    View view;
    public final Activity activity;
    public ListerModel model;
    public Button shareButton;
    public int id;

    public ListDetailsView(View view, ListerModel model, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.model = model;
        this.activity = activity;
        Intent intent = activity.getIntent();
        id = intent.getIntExtra(ListOverviewViewController.LIST_ID, 0);
        Log.d("Test detailview id", String.valueOf(id));

        // Setup the rest of the view layout
//        listElement1 = (CheckBox) activity.findViewById(R.id.list_element_1);

        TodoList todoList = model.getTodoById(id);
        List<ListItem> listItems = todoList.getListItems();
        LinearLayout detailsContainer = (LinearLayout) activity.findViewById(R.id.details_container);

        LinearLayout listDetailsHeader = (LinearLayout) View.inflate(activity.getBaseContext(),
                R.layout.list_details_header, null);
        TextView listTitle = (TextView) listDetailsHeader.findViewById(R.id.list_name);
        TextView deadline = (TextView) listDetailsHeader.findViewById(R.id.deadline);
        listTitle.setText(todoList.getTitle());
        deadline.setText("Deadline " + todoList.getDeadline());
        detailsContainer.addView(listDetailsHeader);

        LinearLayout itemsContainer = (LinearLayout) View.inflate(activity.getBaseContext(),
                R.layout.list_details_content, null);
//        LinearLayout itemsContainer = (LinearLayout) activity.findViewById(R.id.details_items_container);
        detailsContainer.addView(itemsContainer);

        // Fetch all the items in the list and add them to the items container.
        for (ListItem listItem : listItems) {
            CheckBox checkBox = (CheckBox) View.inflate(activity.getBaseContext(),
                    R.layout.list_element, null);
            checkBox.setText(listItem.getContent());
            checkBox.setChecked(listItem.isChecked());
            itemsContainer.addView(checkBox);
        }

        LinearLayout listDetailsBottom = (LinearLayout) View.inflate(activity.getBaseContext(),
                R.layout.list_details_bottom, null);
        TextView lastChanged = (TextView) listDetailsBottom.findViewById(R.id.last_changed);
        shareButton = (Button) listDetailsBottom.findViewById(R.id.share_button);
        lastChanged.setText("Last changed: " + todoList.getLastChange());
        detailsContainer.addView(listDetailsBottom);

    }
}
