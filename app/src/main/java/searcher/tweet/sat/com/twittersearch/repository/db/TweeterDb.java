package searcher.tweet.sat.com.twittersearch.repository.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import searcher.tweet.sat.com.twittersearch.repository.db.dao.StatusDao;
import searcher.tweet.sat.com.twittersearch.repository.db.dao.UserDao;
import searcher.tweet.sat.com.twittersearch.repository.db.model.StatusRow;
import searcher.tweet.sat.com.twittersearch.repository.db.model.UserRow;

@Database(entities = {StatusRow.class, UserRow.class}, version = 1)
public abstract class TweeterDb extends RoomDatabase {

    private static String DB_NAME = "TwitterDB";

    private static TweeterDb sTweeterDb;

    /**
     * @param context
     * @return
     */
    public static synchronized TweeterDb getTwitterDbInstance(Context context) {

        if (sTweeterDb == null) {
            sTweeterDb = Room.databaseBuilder(context, TweeterDb.class, DB_NAME)
                    .build();
        }

        return sTweeterDb;

    }

    public abstract StatusDao statusDao();

    public abstract UserDao userDao();
}

