package se.dxtr.lister.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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
        for (ListData.TodoListData data: listData.getTodoListDatas()){
            TodoList todoList = new TodoList(data.getId(), data.getTitle(), data.getAuthor(),
                    data.getDeadline(), data.getLastChange());
            for (ListData.ListItemData itemData: data.getListItemDatas()){
                ListItem listItem = new ListItem(itemData.getId(),
                        itemData.isChecked(), itemData.getContent());
                todoList.addListItem(listItem);
            }
            for (ListData.CollaboratorData collabData: data.getCollaboratorDatas()){
                User user = new User(collabData.getId(), collabData.getName());
                todoList.addCollaborator(user);
            }
            todoLists.add(todoList);
        }

        for (TodoList todoList: getTodoLists()) {
            Log.d("Test lists", todoList.toString());
        }
    }

}
