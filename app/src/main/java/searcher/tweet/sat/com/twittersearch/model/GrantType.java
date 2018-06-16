package searcher.tweet.sat.com.twittersearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrantType {

    @SerializedName("grant_type")
    @Expose
    public String grant_type = "client_credentials";

    public GrantType() {

    }

}
