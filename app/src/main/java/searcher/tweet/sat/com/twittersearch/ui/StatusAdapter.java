package searcher.tweet.sat.com.twittersearch.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import searcher.tweet.sat.com.twittersearch.model.Status;
import twitter.sat.com.twittersearch.R;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {

    ExecutorService mExecutor;
    WeakReference<Context> mContextWeakReference;
    private List<Status> mList;

    public StatusAdapter(List<Status> list) {
        mList = list;
        mExecutor = Executors.newFixedThreadPool(10);
        // mContextWeakReference = new WeakReference<>(context);
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_status, parent, false);

        StatusViewHolder vh = new StatusViewHolder(v);
        return vh;
    }

    public void setList(List<Status> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {

        if (mList != null && mList.size() > position) {
            Status status = mList.get(position);
            holder.tvTweet.setText(status.getText());
            holder.tvUser.setText(status.getUser().getName());
            holder.tvFollowers.setText(status.getUser().getFollowersCount() + " Followers");
            holder.tvFriends.setText(status.getUser().getFriendsCount() + " Friends");
            holder.tvUrl.setText(status.getUser().getUrl());
            holder.tvRetweets.setText("Retweets:" + status.getRetweetCount());
            holder.tvLikes.setText("Likes:" + status.getFavoriteCount());
            loadImage(holder.imageProfile, status.getUser().getProfileImageUrl());


        }

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public void loadImage(final ImageView imageView, final String urlstr) {

        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(urlstr);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imageView.setImageBitmap(bmp);
            }
        });


    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTweet)
        TextView tvTweet;

        @BindView(R.id.tvUser)
        TextView tvUser;
        /* @BindView(R.id.layoutprofile)
         LinearLayout layoutProfile;*/
        @BindView(R.id.tvFollowers)
        TextView tvFollowers;
        @BindView(R.id.tvFriends)
        TextView tvFriends;
        @BindView(R.id.tvUrl)
        TextView tvUrl;

        @BindView(R.id.imageViewProfile)
        ImageView imageProfile;

        @BindView(R.id.tvRetweets)
        TextView tvRetweets;
        @BindView(R.id.tvLikes)
        TextView tvLikes;

      /*  @BindView(R.id.linearLayoutParent)
        LinearLayout linearLayoutParent;*/

      /*  @BindView(R.id.btExpand)
        Button btExpand;*/


        public StatusViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        /*@OnClick(R.id.btExpand)
        public void showOrHide(){

            if(imageProfile.getVisibility()==View.INVISIBLE || imageProfile.getVisibility()==View.GONE){
                imageProfile.setVisibility(View.VISIBLE);
                btExpand.setText("Collapse");
                tvFriends.setVisibility(View.VISIBLE);
                tvFollowers.setVisibility(View.VISIBLE);
                tvUrl.setVisibility(View.VISIBLE);

            }else{
                imageProfile.setVisibility(View.GONE);
                btExpand.setText("Expand");
                tvFollowers.setVisibility(View.GONE);
                tvUrl.setVisibility(View.GONE);
                tvFriends.setVisibility(View.GONE);
            }
        }*/

    }
}
