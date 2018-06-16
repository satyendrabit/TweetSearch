package searcher.tweet.sat.com.twittersearch.repository.db.repository;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import searcher.tweet.sat.com.twittersearch.Constants;
import searcher.tweet.sat.com.twittersearch.TwitterApplication;
import searcher.tweet.sat.com.twittersearch.model.AccessToken;
import searcher.tweet.sat.com.twittersearch.model.Status;
import searcher.tweet.sat.com.twittersearch.model.TwitterStatus;
import searcher.tweet.sat.com.twittersearch.repository.db.TweeterDb;
import searcher.tweet.sat.com.twittersearch.repository.db.api.TwitterApi;
import searcher.tweet.sat.com.twittersearch.repository.db.api.TwitterService;
import searcher.tweet.sat.com.twittersearch.repository.db.model.StatusRow;
import searcher.tweet.sat.com.twittersearch.viewmodels.TweetsListener;

public class StatusRepository {

    public static String TAG = "StatusRepository";
    TweeterDb mTweeterDb;
    TweetsListener mTweetListener;
    Executor executor;

    public StatusRepository(TweeterDb tweeterDb, TweetsListener listener) {
        mTweeterDb = tweeterDb;
        mTweetListener = listener;
        executor = Executors.newSingleThreadExecutor();
    }

    public List<StatusRow> getAllStatusFromDb() {
        return mTweeterDb.statusDao().getAllStatus();
    }

    public List<Status> getStatusBetweenRange(int start, int end) {
        List<StatusRow> list = getAllStatusFromDb();
        List<Status> statusList = getStatusListFromStatusRow(list);
        return statusList.subList(start, end);
    }

    public void getStatusesFromTweeter(final String query, final String resulttype) {
        TwitterService twitterService = TwitterApi.getTwitterApiClient(TwitterApplication.getInstance()).create(TwitterService.class);
        twitterService.getPopularTweets(query, resulttype).enqueue(new Callback<TwitterStatus>() {
            @Override
            public void onResponse(Call<TwitterStatus> call, final Response<TwitterStatus> response) {
                Log.i(TAG, "onResponse: " + response.code() + response.errorBody());

                if (response.code() == 401) {
                    getToken(query, resulttype);
                } else if (response.code() == 200) {

                    if (mTweetListener != null) {
                        mTweetListener.onTweetStatusRecieved(response.body().getStatuses(), false);
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "run: Saving to DB");
                                saveTweetstoDb(response.body());
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<TwitterStatus> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                mTweetListener.onError(500, t.getMessage());
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Status> list = getStatusListFromStatusRow(getAllStatusFromDb());
                        mTweetListener.onTweetStatusRecieved(list, true);
                    }
                });

            }
        });
    }


    public void getToken(final String query, final String resultype) {
        Log.i(TAG, "getToken: Calling GetToken");
        TwitterService twitterService = TwitterApi.getTwitterOuthApiClient(TwitterApplication.getInstance()).create(TwitterService.class);
        twitterService.getAccessToken("client_credentials").enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                try {
                    Log.i(TAG, "onResponse: " + response.code() + " , " + response.body() + " IS SUCCES : " + response.isSuccessful() + response.errorBody().string().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response.isSuccessful() && response.code() == 200) {

                    TwitterApplication.getInstance().getTwitterSharedPref().
                            setString(Constants.ACCESS_TOKEN, response.body().getTokenType() + " " +
                                    response.body().getAccessToken());
                    getStatusesFromTweeter(query, resultype);
                }

            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });

    }


    private List<Status> getStatusListFromStatusRow(List<StatusRow> list) {
        List<Status> statusList = new ArrayList<>();
        Gson gson = new Gson();
        for (StatusRow row : list) {
            Status status = gson.fromJson(row.getJsonData(), Status.class);
            statusList.add(status);
        }
        return statusList;
    }

    private void saveTweetstoDb(TwitterStatus twitterStatus) {

        List<StatusRow> list = new ArrayList<>();
        List<Status> statusList = twitterStatus.getStatuses();
        Gson gson = new Gson();
        for (Status status : statusList) {
            String jsonstring = gson.toJson(status);
            StatusRow row = new StatusRow(status.getId(), jsonstring);
            list.add(row);
        }
        mTweeterDb.statusDao().insert(list);
    }
}
