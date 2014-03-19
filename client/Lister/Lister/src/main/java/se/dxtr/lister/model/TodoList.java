package se.dxtr.lister.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dexter on 2014-03-19.
 */
public class TodoList {
    private int id;
    private String title;
    private int author;
    private Date deadline;
    private Date lastChange;
    private List<ListItem> listItems;

    public TodoList(int id, String title, int author, Date deadline, Date lastChange) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.deadline = deadline;
        this.lastChange = lastChange;
        listItems = new ArrayList<ListItem>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthor() {
        return author;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getLastChange() {
        return lastChange;
    }
}
