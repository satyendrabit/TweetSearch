package searcher.tweet.sat.com.twittersearch;

import android.app.Application;

public class TwitterApplication extends Application {


    private static TwitterApplication mTwitterApplication;
    private TwitterSharedPref mTwitterSharedPref;

    public static TwitterApplication getInstance() {
        return mTwitterApplication;
    }

    public TwitterSharedPref getTwitterSharedPref() {
        return mTwitterSharedPref;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTwitterSharedPref = new TwitterSharedPref(this);
        mTwitterApplication = this;

    }


}
