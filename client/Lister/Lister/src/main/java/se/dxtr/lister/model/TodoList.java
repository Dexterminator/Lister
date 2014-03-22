package se.dxtr.lister.model;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private List<User> collaborators;

    public TodoList(int id, String title, int author, Date deadline, Date lastChange) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.deadline = deadline;
        this.lastChange = lastChange;
        listItems = new ArrayList<ListItem>();
        collaborators = new ArrayList<User>();
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

    public List<ListItem> getListItems() {
        return listItems;
    }

    public List<User> getCollaborators() {
        return collaborators;
    }

    public void addListItem(ListItem listItem) {
        listItems.add(listItem);
    }

    public void addCollaborator(User user) {
        collaborators.add(user);
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public void removeListItem(ListItem listItem) {
        listItems.remove(listItem);
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", deadline='" + deadline + '\'' +
                ", lastChange='" + lastChange + '\'' +
                ", listItems=" + listItems +
                ", collaborators=" + collaborators +
                '}';
    }

    public String getLastChangeString() {
        Date dateOfChange = getLastChange();
        // Specify the desired date format
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        // Create object of SimpleDateFormat and pass the desired date format.
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        /*
         * Use format method of SimpleDateFormat class to format the date.
         */
        return sdf.format(dateOfChange);
    }

    public String getDeadlineString() {
        Date dateOfDeadline = getDeadline();
        // Specify the desired date format
        String DATE_FORMAT = "yyyy-MM-dd";
        // Create object of SimpleDateFormat and pass the desired date format.
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        /*
         * Use format method of SimpleDateFormat class to format the date.
         */
        if (dateOfDeadline == null){
            return "";
        }
        return sdf.format(dateOfDeadline);
    }

    public int getDaysUntilDeadline() {
        if (deadline == null){
            return Integer.MAX_VALUE;
        }
        Date deadline = getDeadline();
        Calendar cal = Calendar.getInstance();
        cal.setTime(deadline);

        DateTime dt = new DateTime(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), 0, 0);

        int difference = Days.daysBetween(DateTime.now(), dt).getDays();
        Log.d("Day difference: ", String.valueOf(difference));
        return difference;
    }

    public ListItem getListItemById(int id) {
        for (ListItem listItem: listItems) {
            if (listItem.getItemID() == id) {
                return listItem;
            }
        }
        return null;
    }

}
