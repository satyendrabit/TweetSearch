package searcher.tweet.sat.com.twittersearch.viewmodels;

import java.util.List;

import searcher.tweet.sat.com.twittersearch.model.Status;

public class TweetsData {

    List<Status> mTweetList;
    boolean mIsChached;

    public TweetsData(List<Status> list, boolean cached) {
        mTweetList = list;
        mIsChached = cached;
    }

    public List<Status> getTweetList() {
        return mTweetList;
    }

    public boolean isIsChached() {
        return mIsChached;
    }


}
