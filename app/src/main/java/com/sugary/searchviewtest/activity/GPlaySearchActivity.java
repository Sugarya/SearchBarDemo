package com.sugary.searchviewtest.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.sugary.searchviewtest.R;
import com.sugary.searchviewtest.utils.KeyboardUtils;

import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;

/**
 * Created by Sugarya 2017/03/26
 * 仿Google Play搜索交互效果
 */
public class GPlaySearchActivity extends AppCompatActivity {

    private static final String TAG = "GPlaySearchActivity";

    private RevealFrameLayout mRevealFrameLayout;
    private RelativeLayout mRlSearchBar;
    private EditText mEdSearch;
    private Toolbar mToolbar;

    private boolean mShowSearchToolbar = false;
    private FrameLayout mFrameBodyCover;


    public static Intent getCallingIntent(Context context){
        Intent intent = new Intent(context,GPlaySearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gplay);

        initView();
    }

    private void initView() {
        registerView();
        initToolbar();
        adaptStatusBarAndSearchBar();
    }

    private void initToolbar() {
        mToolbar.setTitle("仿Google Play搜索");
        mToolbar.setSubtitle("");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        setSupportActionBar(mToolbar);
    }

    private void registerView() {
        mEdSearch = (EditText) findViewById(R.id.ed_header_search);
        mRevealFrameLayout = (RevealFrameLayout) findViewById(R.id.rframe_main);
        mRlSearchBar = (RelativeLayout)findViewById(R.id.rl_gplay_search_bar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_gplay_search);
        mFrameBodyCover = (FrameLayout)findViewById(R.id.frame_body_cover);
    }

    /**
     * Android 4.4时，设置状态栏透明，来适配标题栏,搜索栏
     */
    private void adaptStatusBarAndSearchBar() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (mToolbar != null) {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

                int toolbarHeight = (int)getResources().getDimension(R.dimen.tool_bar_height_adapt);
                ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
                layoutParams.height = toolbarHeight;

                int toolbarPaddingTop = (int) getResources().getDimension(R.dimen.tool_bar_padding_top);
                mToolbar.setPadding(0, toolbarPaddingTop, 0, 0);

                int marginTopValue = (int) getResources().getDimension(R.dimen.tool_bar_padding_top);
                RelativeLayout.MarginLayoutParams marginLayoutParams = (RelativeLayout.MarginLayoutParams)mRlSearchBar.getLayoutParams();
                marginLayoutParams.topMargin = marginTopValue;
            }
        }
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

    /**
     * 点击搜索
     */
    private void reactionToClickSearchAction() {
        mShowSearchToolbar = true;

        View childView = mRevealFrameLayout.getChildAt(0);
        childView.setVisibility(View.VISIBLE);
        childView.bringToFront();

        int centerX = childView.getRight();
        int centerY = childView.getBottom() / 2;
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(childView, centerX, centerY, 0, childView.getWidth());
        circularReveal.setDuration(300).setInterpolator(new LinearInterpolator());
        circularReveal.start();

        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                reactionToCover(true);

                mEdSearch.requestFocus();
                KeyboardUtils.showSoftInput(mEdSearch, GPlaySearchActivity.this);
            }
        });
    }

    private boolean reactionToBackPressed() {
        if (mShowSearchToolbar) {
            KeyboardUtils.hideSoftInput(mEdSearch, this);

            View childView = mRevealFrameLayout.getChildAt(0);
            childView.bringToFront();

            int centerX = childView.getLeft();
            int centerY = childView.getBottom() / 2;
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(childView, centerX, centerY, 0, childView.getWidth());
            circularReveal.setDuration(300).setInterpolator(new DecelerateInterpolator());

            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    reactionToCover(false);
                }
            });
            circularReveal.start();

            mShowSearchToolbar = false;
            return true;
        }
        return false;
    }

    public void reactionToCover(boolean isDark){
        if(isDark) {
            mFrameBodyCover.setVisibility(View.VISIBLE);
        }else{
            mFrameBodyCover.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (reactionToBackPressed()) return;
        super.onBackPressed();
    }

}
