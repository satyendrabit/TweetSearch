package searcher.tweet.sat.com.twittersearch.repository.db.api;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import searcher.tweet.sat.com.twittersearch.Constants;
import searcher.tweet.sat.com.twittersearch.TwitterApplication;

public class TwitterApi {


    public static final String AUTHORIZATION = "Authorization";
    public static final String BASE_URL = "https://api.twitter.com/";
    /**
     * This is a very bad to keep token in constant, This is only for demo purpose and should be removed.
     */
    public static String AUTHORIZATION_VALUE = "Basic T1B1d0JxNk9ESzc5bW5oOXNrdTFGa2NLUjpkVEhMMEhsZDlJbG9VSEhvUkhsdzYxblhnWW5XVVB2TEtROFJhWmVnZ0I2MVpXVHhVTA==";


    /*
    header 'authorization: OAuth oauth_consumer_key="consumer-key-for-app",
 oauth_nonce="generated-nonce", oauth_signature="generated-signature",
 oauth_signature_method="HMAC-SHA1", oauth_timestamp="generated-timestamp",
 oauth_token="access-token-for-authed-user", oauth_version="1.0"'
     */
    public static String AUTHORIZATION_TOKEN = "bearer AAAAAAAAAAAAAAAAAAAAAF0U6QAAAAAAN7SmMZtVbmioA4OqM6cg4Ov3MDU%3DLRbxuq2bf3yehvBz3KW6hFlRzZn1ojnxbMcVHwd7YTND1uv8o3";
    public static Retrofit retrofit;

    public static synchronized Retrofit getTwitterOuthApiClient(final Context mContext) {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getInterceptorForBasicAuthorization().build())
                    .build();
        }

        return retrofit;
    }


    public static synchronized Retrofit getTwitterApiClient(final Context mContext) {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getInterceptorForTwitterApi(mContext).build())
                    .build();
        }

        return retrofit;
    }


    public static OkHttpClient.Builder getInterceptorForBasicAuthorization() {

        OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder();


        oktHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header(AUTHORIZATION, AUTHORIZATION_VALUE)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        return oktHttpClient;
    }


    public static OkHttpClient.Builder getInterceptorForTwitterApi(Context context) {

        OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder();
        final String accessToken = TwitterApplication.getInstance().getTwitterSharedPref().getString(Constants.ACCESS_TOKEN, AUTHORIZATION_TOKEN);


        oktHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header(AUTHORIZATION, accessToken)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        return oktHttpClient;
    }
}
