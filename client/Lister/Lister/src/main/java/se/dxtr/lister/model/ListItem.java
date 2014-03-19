package se.dxtr.lister.model;

/**
 * Created by Dexter on 2014-03-19.
 */
public class ListItem {
    private int itemID;
    private boolean checked;
    private String content;

    public ListItem(int itemID, boolean checked, String content) {
        this.itemID = itemID;
        this.checked = checked;
        this.content = content;
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

    @Override
    public String toString() {
        return "ListItem{" +
                "itemID=" + itemID +
                ", checked=" + checked +
                ", content='" + content + '\'' +
                '}';
    }
}
