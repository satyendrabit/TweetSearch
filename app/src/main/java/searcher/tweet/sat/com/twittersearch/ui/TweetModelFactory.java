package searcher.tweet.sat.com.twittersearch.ui;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import searcher.tweet.sat.com.twittersearch.viewmodels.TweetViewModel;


public class TweetModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Context mContext;
    private TweetRecieviedListener mParam;


    public TweetModelFactory(Context context, TweetRecieviedListener listener) {
        mContext = context;
        mParam = listener;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new TweetViewModel(mContext, mParam);
    }
}