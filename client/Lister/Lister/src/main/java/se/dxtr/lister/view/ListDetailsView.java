package se.dxtr.lister.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.dxtr.lister.R;
import se.dxtr.lister.controller.ListOverviewViewController;
import se.dxtr.lister.model.ListItem;
import se.dxtr.lister.model.ListerModel;
import se.dxtr.lister.model.TodoList;

public class ListDetailsView {
    View view;
    public final Activity activity;
    public ListerModel model;
    public Button shareButton, newItemButton;
    public int id;
    public Map<CheckBox, Integer> listElements;
    public TextView lastChanged;

    public ListDetailsView(View view, ListerModel model, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.model = model;
        this.activity = activity;
        listElements = new HashMap<CheckBox, Integer>();
        Intent intent = activity.getIntent();
        id = intent.getIntExtra(ListOverviewViewController.LIST_ID, 0);
        Log.d("Test detailview id", String.valueOf(id));
    }

    public void update() {
        Log.d("Details updated", "yup");
        listElements = new HashMap<CheckBox, Integer>();
        TodoList todoList = model.getTodoById(id);
        List<ListItem> listItems = todoList.getListItems();
        LinearLayout detailsContainer = (LinearLayout) activity.findViewById(R.id.details_container);
        detailsContainer.removeAllViews();
        LinearLayout listDetailsHeader = (LinearLayout) View.inflate(activity.getBaseContext(),
                R.layout.list_details_header, null);
        TextView listTitle = (TextView) listDetailsHeader.findViewById(R.id.list_name);
        TextView deadline = (TextView) listDetailsHeader.findViewById(R.id.deadline);
        listTitle.setText(todoList.getTitle());
        deadline.setText("Deadline " + todoList.getDeadlineString());
        detailsContainer.addView(listDetailsHeader);

        if (todoList.getDaysUntilDeadline() <= 1) {
            deadline.setTextColor(Color.RED);
        }

        LinearLayout itemsContainer = (LinearLayout) View.inflate(activity.getBaseContext(),
                R.layout.list_details_content, null);
        LinearLayout itemsScroll = (LinearLayout) itemsContainer.findViewById(R.id.details_items_container);
        detailsContainer.addView(itemsContainer);

        // Fetch all the items in the list and add them to the items container.
        for (ListItem listItem : listItems) {
            CheckBox checkBox = (CheckBox) View.inflate(activity.getBaseContext(),
                    R.layout.list_element, null);
            checkBox.setText(listItem.getContent());
            checkBox.setChecked(listItem.isChecked());
            itemsScroll.addView(checkBox);
            listElements.put(checkBox, listItem.getItemID());
        }

        LinearLayout listDetailsBottom = (LinearLayout) View.inflate(activity.getBaseContext(),
                R.layout.list_details_bottom, null);
        lastChanged = (TextView) listDetailsBottom.findViewById(R.id.last_changed);
        newItemButton = (Button) listDetailsBottom.findViewById(R.id.new_item_button);
        shareButton = (Button) listDetailsBottom.findViewById(R.id.share_button);
        lastChanged.setText("Last changed: " + todoList.getLastChangeString());
        detailsContainer.addView(listDetailsBottom);
    }
}
