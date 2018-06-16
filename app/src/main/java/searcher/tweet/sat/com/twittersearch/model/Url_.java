package searcher.tweet.sat.com.twittersearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Url_ {

    @SerializedName("urls")
    @Expose
    private List<Url__> urls = new ArrayList<Url__>();

    public List<Url__> getUrls() {
        return urls;
    }

    public void setUrls(List<Url__> urls) {
        this.urls = urls;
    }

}
