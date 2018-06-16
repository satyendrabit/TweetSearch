package searcher.tweet.sat.com.twittersearch.repository.db.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import searcher.tweet.sat.com.twittersearch.model.AccessToken;
import searcher.tweet.sat.com.twittersearch.model.TwitterStatus;

public interface TwitterService {

    @GET("1.1/search/tweets.json?")
    Call<TwitterStatus> getPopularTweets(@Query("q") String query,
                                         @Query("result_type") String resulttype);

    @POST("oauth2/token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(@Field("grant_type") String granttype);
}
