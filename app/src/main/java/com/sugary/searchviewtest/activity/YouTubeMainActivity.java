package com.sugary.searchviewtest.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.sugary.searchviewtest.ChangeColor;
import com.sugary.searchviewtest.R;
import com.sugary.searchviewtest.adapter.YoutubePagerAdapter;
import com.sugary.searchviewtest.fragment.AccountFragment;
import com.sugary.searchviewtest.fragment.FashionFragment;
import com.sugary.searchviewtest.fragment.HomeFragment;
import com.sugary.searchviewtest.fragment.SubscriptionFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YouTubeMainActivity extends AppCompatActivity {

    public static final String SHARE_APP_BAR = "share_app_bar";
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private List<String> mPagerTitleList;

    private static final int[] sTabIconArray = new int[]{
            R.drawable.tab_icon,
            R.drawable.tab_icon,
            R.drawable.tab_icon,
            R.drawable.tab_icon
    };
    private static final String[] sToolbarTitleArray = new String[]{"首页", "时下流行", "订阅内容", "账号"};

    private static final int[] sAppBarColorArray = new int[]{
            R.color.app_bar_color_0,
            R.color.app_bar_color_1,
            R.color.app_bar_color_2,
            R.color.app_bar_color_3
    };

    private AppBarLayout mAppBarLayout;
    private TabLayout mTabLayout;

    public static Intent getCallingIntent(Context context){
        Intent intent = new Intent(context,YouTubeMainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_you_tube);

        initView();
        initShareElementTransition();
    }

    private void initView() {
        initToolbar();
        initViewPager();
        initTabLayout();
    }

    private void initShareElementTransition() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition exitTransition = TransitionInflater.from(this).inflateTransition(R.transition.exit);
            getWindow().setSharedElementReenterTransition(new Fade(Fade.IN));
            getWindow().setSharedElementExitTransition(new Fade(Fade.OUT));
            getWindow().setSharedElementEnterTransition(new Fade(Fade.IN));
            getWindow().setSharedElementReturnTransition(new Fade(Fade.OUT));
        }
    }

    private void initToolbar() {
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_you_tube);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("首页");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.pager_you_tube);
        mPagerTitleList = new ArrayList<>();
        mPagerTitleList.addAll(Arrays.asList(sToolbarTitleArray));

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(FashionFragment.newInstance());
        fragmentList.add(SubscriptionFragment.newInstance());
        fragmentList.add(AccountFragment.newInstance());

        YoutubePagerAdapter pagerAdapter = new YoutubePagerAdapter(getSupportFragmentManager(), fragmentList);

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mToolbar.setTitle(mPagerTitleList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTabLayout() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_you_tube);
        mTabLayout.setupWithViewPager(mViewPager);

        int tabCount = mTabLayout.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            mTabLayout.getTabAt(i).setIcon(sTabIconArray[i]);
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
                ActivityCompat.startActivity(this, YoutubeSearchActivity.getCallingIntent(this), getOptionBundle());
                break;
        }

        return true;
    }

    private Bundle getOptionBundle() {
//        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, mAppBarLayout, SHARE_APP_BAR).toBundle();
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                mAppBarLayout,
                YouTubeMainActivity.SHARE_APP_BAR
        ).toBundle();

        return bundle;
    }


}
