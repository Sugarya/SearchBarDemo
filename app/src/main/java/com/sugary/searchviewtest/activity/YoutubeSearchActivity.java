package com.sugary.searchviewtest.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;

import com.sugary.searchviewtest.R;

public class YoutubeSearchActivity extends AppCompatActivity {




    public static Intent getCallingIntent(Activity activity){
        Intent intent = new Intent(activity,YoutubeSearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_search);

        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.app_bar_search);
        ViewCompat.setTransitionName(appBarLayout,YouTubeMainActivity.SHARE_APP_BAR);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_gray));
        }

        initShareElementTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initShareElementTransition() {

    }


    @Override
    public void onBackPressed() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            finishAfterTransition();
            return;
        }
        super.onBackPressed();
    }


}
