package searcher.tweet.sat.com.twittersearch.repository.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import searcher.tweet.sat.com.twittersearch.repository.db.model.StatusRow;

@Dao
public interface StatusDao {


    @Query("SELECT * FROM statusrow")
    List<StatusRow> getAllStatus();

    @Insert
    void insert(StatusRow... statuses);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<StatusRow> list);

    @Query("DELETE  FROM statusrow")
    void deleteAll();

}
