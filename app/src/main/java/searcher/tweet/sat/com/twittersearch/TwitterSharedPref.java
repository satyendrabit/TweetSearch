package searcher.tweet.sat.com.twittersearch;

import android.content.Context;
import android.content.SharedPreferences;

public class TwitterSharedPref {

    public static String SHARED_PREF = "TwitterSharedPref";
    private static TwitterSharedPref sTwitterSharedPref;
    SharedPreferences sharedPreferences;

    public TwitterSharedPref(Context context) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    }


    public void setString(String key, String value) {

        sharedPreferences.edit().putString(key, value).commit();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }


}
