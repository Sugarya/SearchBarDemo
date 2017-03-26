package com.sugary.searchviewtest.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sugary.searchviewtest.R;
import com.sugary.searchviewtest.utils.KeyboardUtils;

import io.codetail.widget.RevealFrameLayout;

/**
 * Created by Sugarya　 2017/03/26
 * 仿Google邮箱搜索交互效果
 */
public class GMailSearchActivity extends AppCompatActivity {

    private EditText mEdSearch;
    private RevealFrameLayout mRevealFrameLayout;

    private boolean mIsShowSearch = false;
    private LinearLayout mLlSearchBar;


    public static Intent getCallingIntent(Context context){
        Intent intent = new Intent(context,GMailSearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail);

        initView();
    }

    private void initView() {
        mRevealFrameLayout = (RevealFrameLayout) findViewById(R.id.rfl_gmail_main);
        mEdSearch = (EditText) findViewById(R.id.ed_header_search);

        mLlSearchBar = (LinearLayout)findViewById(R.id.ll_search_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        toolbar.setTitle("仿Gmail邮箱搜索");
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_search:
                reactionToClickSearchAction();
                break;
        }
        return true;
    }

    private void reactionToClickSearchAction() {
        View childView = mRevealFrameLayout.getChildAt(0);
        childView.bringToFront();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_gray));
        }

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLlSearchBar, "alpha", 0, 1);
        objectAnimator.setDuration(300).setInterpolator(new FastOutSlowInInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mIsShowSearch = true;

                mEdSearch.requestFocus();
                KeyboardUtils.showSoftInput(mEdSearch, GMailSearchActivity.this);
            }
        });

        objectAnimator.start();
    }

    @Override
    public void onBackPressed() {
        if (mIsShowSearch) {
            mEdSearch.clearFocus();
            KeyboardUtils.hideSoftInput(mEdSearch, this);

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLlSearchBar, "alpha", 1, 0);
            objectAnimator.setDuration(200).setInterpolator(new FastOutSlowInInterpolator());
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    View childView = mRevealFrameLayout.getChildAt(0);
                    childView.bringToFront();
                }
            });
            objectAnimator.start();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.emailColorPrimaryDark));
            }
            mIsShowSearch = false;

            return;
        }
        super.onBackPressed();
    }
}
