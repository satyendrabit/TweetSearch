package searcher.tweet.sat.com.twittersearch.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import searcher.tweet.sat.com.twittersearch.model.Status;
import searcher.tweet.sat.com.twittersearch.viewmodels.TweetViewModel;
import searcher.tweet.sat.com.twittersearch.viewmodels.TweetsData;
import twitter.sat.com.twittersearch.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class TwittListFragment extends Fragment implements TweetRecieviedListener {

    public static String FRAGMENT_TAG = "TwittListFragment";
    public static String RESULT_TYPE = "popular";

    @BindView(R.id.recylerView)
    RecyclerView mRecyclerView;
    List<Status> mList;
    TweetViewModel mTweetViewModel;
    @BindView(R.id.edQuery)
    EditText edQuery;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.tvNoTweets)
    TextView tvNoTweetsMessage;
    private StatusAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public TwittListFragment() {
    }

    public static TwittListFragment getInstance() {
        return new TwittListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // mRecyclerView = (RecyclerView) view.findViewById(R.id.recylerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        ButterKnife.bind(this, view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mList = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new StatusAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mTweetViewModel = ViewModelProviders.of(this, new TweetModelFactory(getContext(), this)).get(TweetViewModel.class);
        initialize();
        return view;
    }

    public void initialize() {
        showProgress();
        mTweetViewModel.getLiveTweets().observe(this, new Observer<TweetsData>() {
            @Override
            public void onChanged(@Nullable TweetsData tweetsData) {

                if (tweetsData == null || tweetsData.getTweetList() == null) {
                    showNoTweetsAvailbe("No Tweets Available");
                } else if (tweetsData.getTweetList().size() == 0) {
                    showNoTweetsAvailbe("No Tweets Available");
                } else {//(tweetsData.getTweetList().size() > 0) {
                    mAdapter.setList(tweetsData.getTweetList());
                    showList();
                    if (tweetsData.isIsChached()) {
                        Toast.makeText(getContext(), "Network Problem, showing cached data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mTweetViewModel.getTweetsFromTweeter("nasa", RESULT_TYPE);
    }


    @Override
    public void gotTweets(final TweetsData tweetsData) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTweetViewModel.getLiveTweets().setValue(tweetsData);
                }
            });
        }


    }


    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        tvNoTweetsMessage.setVisibility(View.GONE);

    }

    public void showList() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        tvNoTweetsMessage.setVisibility(View.GONE);

    }

    public void showNoTweetsAvailbe(String message) {
        tvNoTweetsMessage.setText(message);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        tvNoTweetsMessage.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.btSearch)
    public void search() {

        String query = edQuery.getText().toString();
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(getContext(), "Please enter a query to search", Toast.LENGTH_SHORT).show();
            return;

        }
        showProgress();
        mTweetViewModel.getTweetsFromTweeter(query, RESULT_TYPE);
    }


}
