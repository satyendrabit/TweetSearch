package searcher.tweet.sat.com.twittersearch.viewmodels;

import java.util.List;

import searcher.tweet.sat.com.twittersearch.model.Status;


public interface TweetsListener {

    void onTweetStatusRecieved(List<Status> list, boolean isCachedDb);

    void onError(int responsecode, String errorMessage);
}