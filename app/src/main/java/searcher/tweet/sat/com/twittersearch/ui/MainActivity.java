package searcher.tweet.sat.com.twittersearch.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import twitter.sat.com.twittersearch.R;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar toolbar = findViewById(R.id.toolbar);
        //setActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                TwittListFragment.getInstance(), TwittListFragment.FRAGMENT_TAG)
                .commit();

    }

}
