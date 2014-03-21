package se.dxtr.lister.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dexter on 2014-03-19.
 */
public class User {
    private Date dateCreated;
    private int id;
    private String name;

    public User(int id, String name, Date dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getDateCreatedString() {
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date d = getDateCreated();
        String creationDate = df.format(d);

        return creationDate;
    }

}
