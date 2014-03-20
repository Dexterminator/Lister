package se.dxtr.lister.model;

/**
 * Created by Dexter on 2014-03-19.
 */
public class User {
    private String dateCreated;
    private int id;
    private String name;

    public User(int id, String name, String dateCreated) {
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

    public String getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
