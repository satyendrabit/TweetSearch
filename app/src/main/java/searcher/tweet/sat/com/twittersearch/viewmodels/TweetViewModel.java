package searcher.tweet.sat.com.twittersearch.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import java.util.List;

import searcher.tweet.sat.com.twittersearch.model.Status;
import searcher.tweet.sat.com.twittersearch.repository.db.TweeterDb;
import searcher.tweet.sat.com.twittersearch.repository.db.repository.StatusRepository;
import searcher.tweet.sat.com.twittersearch.ui.TweetRecieviedListener;

public class TweetViewModel extends ViewModel implements TweetsListener {


    MutableLiveData<TweetsData> mMutableList;
    Context mContext;
    TweetRecieviedListener mListener;
    private StatusRepository mStatusRepository;

    public TweetViewModel() {

    }

    public TweetViewModel(Context context, TweetRecieviedListener listener) {
        mStatusRepository = new StatusRepository(
                TweeterDb.getTwitterDbInstance(context), this);
        mMutableList = new MutableLiveData<>();
        mContext = context;
        mListener = listener;
    }

    public MutableLiveData<TweetsData> getLiveTweets() {
        return mMutableList;
    }

    public void getTweetsFromTweeter(String query, String queryResult) {
        mStatusRepository.getStatusesFromTweeter(query, queryResult);
    }


    @Override
    public void onTweetStatusRecieved(List<Status> list, boolean isCachedDb) {
        TweetsData tweetsData = new TweetsData(list, isCachedDb);
        mListener.gotTweets(tweetsData);
        Log.i("TEST", "onTweetStatusRecieved: " + list.size());

    }

    @Override
    public void onError(int responsecode, String errorMessage) {

    }


}
