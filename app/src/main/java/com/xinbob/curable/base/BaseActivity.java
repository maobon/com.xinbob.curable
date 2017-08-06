package com.xinbob.curable.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.xinbob.curable.R;

/**
 * Created by xinbob on 7/17/17.
 * Activity 基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int setContentViewResId();

    public abstract int setOnCreateOptionsMenuResId();

    public abstract void onOptionsItemSelectedHandler(MenuItem item);

    public abstract void preInitViews();

    public abstract void initViews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        setContentView(setContentViewResId());

        preInitViews(); // 初始化Views之前
        initToolbar();  // 初始化Toolbar
        initViews();    // 初始化Views
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (setOnCreateOptionsMenuResId() != 0) {
            getMenuInflater().inflate(setOnCreateOptionsMenuResId(), menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        onOptionsItemSelectedHandler(item);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
}
