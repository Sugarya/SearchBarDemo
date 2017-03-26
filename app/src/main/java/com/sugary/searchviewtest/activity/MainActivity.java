package com.sugary.searchviewtest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.sugary.searchviewtest.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }

    public void onGoogleMailBtnClick(View v){
        startActivity(GMailSearchActivity.getCallingIntent(this));
    }

    public void onGooglePlayBtnClick(View v){
        startActivity(GPlaySearchActivity.getCallingIntent(this));
    }

    public void onYoutubeBtnClick(View v){
        startActivity(YouTubeMainActivity.getCallingIntent(this));
    }

}
