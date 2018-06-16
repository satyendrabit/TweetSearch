package searcher.tweet.sat.com.twittersearch.repository.db.repository;

import com.google.gson.Gson;

import searcher.tweet.sat.com.twittersearch.model.User;
import searcher.tweet.sat.com.twittersearch.repository.db.TweeterDb;
import searcher.tweet.sat.com.twittersearch.repository.db.model.UserRow;

public class UserRepository {

    private TweeterDb mTweeterDb;

    public UserRepository(TweeterDb db) {
        mTweeterDb = db;
    }

    public User getUserFromId(Long id) {
        UserRow userRow = mTweeterDb.userDao().getUserFromId(id);
        Gson gson = new Gson();
        User user = gson.fromJson(userRow.getJsonData(), User.class);
        return user;

    }

    public void insert(User user) {

        Gson gson = new Gson();
        String str = gson.toJson(user);
        mTweeterDb.userDao().insert(new UserRow(user.getId(), str));
    }
}
