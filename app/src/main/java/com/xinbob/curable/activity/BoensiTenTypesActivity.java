package com.xinbob.curable.activity;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xinbob.curable.R;
import com.xinbob.curable.base.BaseActivity;
import com.xinbob.curable.utils.Constants;

/**
 * 伯恩斯十大扭曲介绍
 */

public class BoensiTenTypesActivity extends BaseActivity {

    @Override
    public int setContentViewResId() {
        return R.layout.activity_boensi_ten_types;
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
        ListView lvBoensi = (ListView) findViewById(R.id.lv_boensi);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, Constants.TEN_TYPES);
        lvBoensi.setAdapter(adapter);

        lvBoensi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                String title = Constants.TEN_TYPES[pos];
                String content = createContent(pos + 1);

                Intent intent = new Intent(BoensiTenTypesActivity.this, BoensiDetailActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                startActivity(intent);
            }
        });
    }

    private String createContent(int index) {
        switch (index) {
            case 1:
                return getResources().getString(R.string.boensi_1);
            case 2:
                return getResources().getString(R.string.boensi_2);
            case 3:
                return getResources().getString(R.string.boensi_3);
            case 4:
                return getResources().getString(R.string.boensi_4);
            case 5:
                return getResources().getString(R.string.boensi_5);
            case 6:
                return getResources().getString(R.string.boensi_6);
            case 7:
                return getResources().getString(R.string.boensi_7);
            case 8:
                return getResources().getString(R.string.boensi_8);
            case 9:
                return getResources().getString(R.string.boensi_9);
            case 10:
                return getResources().getString(R.string.boensi_10);
        }
        return null;
    }

}
