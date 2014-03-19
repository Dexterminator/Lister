package se.dxtr.lister.model;

/**
 * Created by Dexter on 2014-03-19.
 */
public class ListItem {
    private int listID;
    private int itemID;
    private boolean checked;
    private String content;

    public ListItem(int listID, int itemID, boolean checked, String content) {
        this.listID = listID;
        this.itemID = itemID;
        this.checked = checked;
        this.content = content;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
