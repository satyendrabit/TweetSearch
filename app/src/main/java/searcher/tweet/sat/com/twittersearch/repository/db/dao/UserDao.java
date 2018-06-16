package searcher.tweet.sat.com.twittersearch.repository.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import searcher.tweet.sat.com.twittersearch.repository.db.model.UserRow;

@Dao
public interface UserDao {

    @Query("Select * from userrow")
    List<UserRow> getAllUser();

    @Query("Select * from userrow where id = :userid ")
    UserRow getUserFromId(long userid);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UserRow user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<UserRow> users);
}
