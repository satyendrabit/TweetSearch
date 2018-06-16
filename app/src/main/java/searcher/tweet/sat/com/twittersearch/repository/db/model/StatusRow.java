package searcher.tweet.sat.com.twittersearch.repository.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class StatusRow {

    @ColumnInfo(name = "jsonData")
    String jsonData;
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private Long id;

    public StatusRow(long id, String jsonData) {
        this.jsonData = jsonData;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }


}
