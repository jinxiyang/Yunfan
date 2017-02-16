package com.yang.yunfan.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yang.yunfan.R;
import com.yang.yunfan.model.Joke;
import com.yang.yunfan.model.ResponeJH;
import com.yang.yunfan.model.ResultJH;
import com.yang.yunfan.source.RemoteDataSourceImpl;
import com.yang.yunfan.ui.base.BaseFragment;
import com.yang.yunfan.widget.LikeOrNotView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class JokeFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_collect_joke)
    ImageView ivCollectJoke;
    @BindView(R.id.iv_joke_detail)
    ImageView ivJokeDetail;
    @BindView(R.id.iv_joke_share)
    ImageView ivJokeShare;
    @BindView(R.id.like_or_not)
    LikeOrNotView likeOrNot;
    @BindView(R.id.btn_more)
    Button btnMore;

    int currPage = 1;
    int pagesize = 20;
    private List<Joke> jokes;

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.i(TAG, "onResult: ");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.i(TAG, "onError: ");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.i(TAG, "onCancel: ");
        }
    };

    public JokeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jokes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_joke, container, false);
        ButterKnife.bind(this, view);
        tvTitle.setText(R.string.joke);
        toolBar.inflateMenu(R.menu.joke_refresh_menu);
        toolBar.getMenu().findItem(R.id.menu_refresh).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_refresh) {
                    refreshJokes();
                }
                return true;
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPage++;
                getJokes();
            }
        });

        likeOrNot.setOnLikeOrNotListener(new LikeOrNotView.OnLikeOrNotListener() {
            @Override
            public void onLike(View view, int position) {
                toggleBtnMoreVisibility(position);
            }

            @Override
            public void onNope(View view, int position) {
                toggleBtnMoreVisibility(position);
            }

            @Override
            public void onAnimationEnd() {
            }
        });

        likeOrNot.setOnItemClickListener(new LikeOrNotView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        ivCollectJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivCollectJoke.getAlpha() < 1){
                    ivCollectJoke.setImageResource(R.drawable.card_uncollected);
                    ivCollectJoke.setAlpha(1f);
                }else {
                    ivCollectJoke.setImageResource(R.drawable.card_collected);
                    ivCollectJoke.setAlpha(0.98f);
                }
            }
        });

        ivJokeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = likeOrNot.getCurrentPosition();
                if (jokes == null || jokes.size() < currentPosition + 1){
                    return;
                }
                Joke joke = jokes.get(currentPosition);
                new ShareAction((AppCompatActivity)getContext())
                        .withTitle(getString(R.string.app_name))
                        .withText(joke.getContent())
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .open();
            }
        });
        return view;
    }

    private void toggleBtnMoreVisibility(int position) {
        if (position == likeOrNot.getAdapter().getCount() - 1){
            btnMore.setVisibility(View.GONE);
        }
    }

    private void refreshJokes() {
        likeOrNot.back();
    }

    @Override
    public void onStart() {
        super.onStart();
        currPage = 1;
        getJokes();
    }

    private void getJokes() {
        Subscription subscription = RemoteDataSourceImpl.getInstance().getJokes(currPage, pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponeJH<ResultJH<Joke>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponeJH<ResultJH<Joke>> respone) {
                        jokes = respone.getResult().getData();
                        if (jokes != null && !jokes.isEmpty()) {
                            Adapter adapter = new Adapter(jokes);
                            likeOrNot.setAdapter(adapter);
                            likeOrNot.invalidateState();
                            btnMore.setVisibility(View.GONE);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    private class Adapter implements LikeOrNotView.IAdapter {

        private List<Joke> jokes;

        public Adapter(List<Joke> jokes) {
            this.jokes = jokes;
        }

        @Override
        public int getCount() {
            return jokes.size();
        }

        @Override
        public View getView(View convertView, ViewGroup parent, int position) {
            if (convertView == null)
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joke, parent, false);
            ((TextView) convertView.findViewById(R.id.tv_joke_content)).setText(jokes.get(position).getContent());
            return convertView;
        }
    }
}
