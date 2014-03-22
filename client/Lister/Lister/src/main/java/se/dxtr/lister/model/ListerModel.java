package se.dxtr.lister.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dexter on 2014-03-19.
 */
public class ListerModel {
    private List<TodoList> todoLists;
    private User loggedInUser;



    public ListerModel() {
        todoLists = new ArrayList<TodoList>();
    }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void loadListData(ListData listData) {
        String expectedFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
        Date deadline = null;
        Date lastChange = null;
        Date dateCreated = null;

        todoLists.clear();
        for (ListData.TodoListData data: listData.getTodoListDatas()){

            // Parse the strings to date.
            try {
                deadline = sdf.parse(data.getDeadline());
                lastChange = sdf.parse(data.getLastChange());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            TodoList todoList = new TodoList(data.getId(), data.getTitle(), data.getAuthor(),
                    deadline, lastChange);
            for (ListData.ListItemData itemData: data.getListItemDatas()){
                ListItem listItem = new ListItem(itemData.getId(),
                        itemData.isChecked(), itemData.getContent());
                todoList.addListItem(listItem);
            }
            for (ListData.CollaboratorData collabData: data.getCollaboratorDatas()){

                // Parse the strings to date.
                try {
                    dateCreated = sdf.parse(collabData.getDateCreated());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                User user = new User(collabData.getId(), collabData.getName(), dateCreated);
                todoList.addCollaborator(user);
            }
            todoLists.add(todoList);
        }

        for (TodoList todoList: getTodoLists()) {
            Log.d("Test lists", todoList.toString());
        }
    }

    public void addTodoList(TodoList todoList){
        todoLists.add(todoList);
    }

    public TodoList getTodoById(int id) {
        List<TodoList> todoLists = getTodoLists();

        for (TodoList todoList: todoLists) {
            if (todoList.getId() == id) {
                return todoList;
            }
        }
        return null;
    }
}
