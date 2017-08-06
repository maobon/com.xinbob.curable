package com.xinbob.curable.activity;

import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.xinbob.curable.R;
import com.xinbob.curable.base.BaseActivity;

/**
 * 查看十大扭曲其中一条的具体内容
 */

public class BoensiDetailActivity extends BaseActivity {


    @Override
    public int setContentViewResId() {
        return R.layout.activity_boensi_detail;
    }

    @Override
    public int setOnCreateOptionsMenuResId() {
        return 0;
    }

    @Override
    public void onOptionsItemSelectedHandler(MenuItem item) {

    }

    @Override
    public void preInitViews() {

    }

    @Override
    public void initViews() {
        String content = getIntent().getStringExtra("content");
        String title = getIntent().getStringExtra("title");

        if (!TextUtils.isEmpty(title)) {
            resetToolbar(title);

            if (title.equals("妄下结论")) {
                content = content + getResources().getString(R.string.boensi_5_a) +
                        getResources().getString(R.string.boensi_5_b);
            }
        }


        if (!TextUtils.isEmpty(content)) {
            ((TextView) findViewById(R.id.tv_boensi_detail)).setText(content);
        }

    }


    private void resetToolbar(String title) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }
}
