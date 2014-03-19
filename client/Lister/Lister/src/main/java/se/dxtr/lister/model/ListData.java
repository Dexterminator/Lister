package se.dxtr.lister.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dexter on 2014-03-19.
 */
public class ListData {

    @SerializedName("lists")
    private TodoListData[] todoListDatas;

    public TodoListData[] getTodoListDatas() {
        return todoListDatas;
    }

    public class TodoListData {
        private int author;
        private String title;
        private String deadline;
        @SerializedName("last_change")
        private String lastChange;
        private int id;
        @SerializedName("items")
        private List<ListItemData> listItemDatas;
        @SerializedName("collaborators")
        private List<CollaboratorData> collaboratorDatas;

        public int getAuthor() {
            return author;
        }
        public String getTitle() {
            return title;
        }
        public String getDeadline() {
            return deadline;
        }
        public String getLastChange() {
            return lastChange;
        }
        public int getId() {
            return id;
        }
        public List<ListItemData> getListItemDatas() {
            return listItemDatas;
        }
        public List<CollaboratorData> getCollaboratorDatas() {
            return collaboratorDatas;
        }

        @Override
        public String toString() {
            return "TodoListData{" +
                    "author=" + author +
                    ", title='" + title + '\'' +
                    ", deadline='" + deadline + '\'' +
                    ", lastChange='" + lastChange + '\'' +
                    ", id=" + id +
                    ", listItemDatas=" + listItemDatas +
                    ", collaboratorDatas=" + collaboratorDatas +
                    '}';
        }
    }

    public class ListItemData {
        private String content;
        private boolean checked;
        private int id;

        public String getContent() {
            return content;
        }
        public boolean isChecked() {
            return checked;
        }
        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "ListItemData{" +
                    "content='" + content + '\'' +
                    ", checked=" + checked +
                    ", id=" + id +
                    '}';
        }
    }

    public class CollaboratorData {
        private String name;
        private int id;
        @SerializedName("date_created")
        private String dateCreated;

        public String getName() {
            return name;
        }
        public int getId() {
            return id;
        }
        public String getDateCreated() {
            return dateCreated;
        }

        @Override
        public String toString() {
            return "CollaboratorData{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", dateCreated='" + dateCreated + '\'' +
                    '}';
        }
    }
}
