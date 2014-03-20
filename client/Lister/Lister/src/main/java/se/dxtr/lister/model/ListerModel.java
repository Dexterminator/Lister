package se.dxtr.lister.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Dexter on 2014-03-19.
 */
public class ListerModel extends Observable {
    private List<TodoList> todoLists;

    public ListerModel() {
        todoLists = new ArrayList<TodoList>();
    }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public void loadListData(ListData listData) {
        todoLists.clear();
        for (ListData.TodoListData data: listData.getTodoListDatas()){
            TodoList todoList = new TodoList(data.getId(), data.getTitle(), data.getAuthor(),
                    data.getDeadline(), data.getLastChange());
            for (ListData.ListItemData itemData: data.getListItemDatas()){
                ListItem listItem = new ListItem(itemData.getId(),
                        itemData.isChecked(), itemData.getContent());
                todoList.addListItem(listItem);
            }
            for (ListData.CollaboratorData collabData: data.getCollaboratorDatas()){
                User user = new User(collabData.getId(), collabData.getName(), collabData.getDateCreated());
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

}
