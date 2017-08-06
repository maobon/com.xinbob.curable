package com.xinbob.curable.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xinbob.curable.R;
import com.xinbob.curable.adapter.TenTypesAdapter;
import com.xinbob.curable.base.BaseActivity;
import com.xinbob.curable.db.Trouble;
import com.xinbob.curable.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 添加记录
 */

public class AddWorryActivity extends BaseActivity {

    private String timeStamp;
    private TenTypesAdapter adapter;
    private EditText etWorry;
    private EditText etResolve;

    private int mood = -1;
    private int id = -1;

    @Override
    public int setContentViewResId() {
        return R.layout.activity_add_worry;
    }

    @Override
    public int setOnCreateOptionsMenuResId() {
        return R.menu.menu_add_worry;
    }

    @Override
    public void onOptionsItemSelectedHandler(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            String worry = etWorry.getText().toString();
            if (!TextUtils.isEmpty(worry)) {
                String resolve = etResolve.getText().toString();
                String tenTypesChoosed = adapter.getCheckedTypes();

                if (insertNewItem(worry, resolve, tenTypesChoosed, mood)) {
                    finish();
                }
            } else {
                Toast.makeText(this, "需要填写消极情绪自述", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void preInitViews() {

    }

    @Override
    public void initViews() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        timeStamp = sdf.format(new Date());

        // ActionBar 当前显示的标题
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            String[] date = timeStamp.split("-");
            String month = date[1].substring(0, 1).equals("0") ?
                    date[1].replace("0", "") : date[1];
            String day = date[2].substring(0, 1).equals("0") ?
                    date[2].replace("0", "") : date[2];
            String week = TimeUtils.getWeek(new Date());
            supportActionBar.setTitle(month + "月" + day + "日 " + week);
        }

        // 十大扭曲选项列表
        final RecyclerView tenTypesChooseArea = (RecyclerView) findViewById(R.id.rv_ten_types_choose_area);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3);
        tenTypesChooseArea.setLayoutManager(manager);
        adapter = new TenTypesAdapter(this);
        tenTypesChooseArea.setAdapter(adapter);

        // 查看十大扭曲介绍
        TextView tvBoensi = (TextView) findViewById(R.id.tv_ten_types_boensi);
        tvBoensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddWorryActivity.this, BoensiTenTypesActivity.class));
            }
        });

        etWorry = (EditText) findViewById(R.id.et_worry);
        etResolve = (EditText) findViewById(R.id.et_resolve);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                switch (checkedId) {
                    case R.id.rb_mood_1:
                        mood = 1;
                        break;
                    case R.id.rb_mood_2:
                        mood = 2;
                        break;
                    case R.id.rb_mood_3:
                        mood = 3;
                        break;
                    case R.id.rb_mood_4:
                        mood = 4;
                        break;
                    case R.id.rb_mood_5:
                        mood = 5;
                        break;
                }

            }
        });

        Trouble undoTrouble = (Trouble) getIntent().getSerializableExtra("undo");

        if (undoTrouble != null) {
            id = undoTrouble.getId();
            etWorry.setText(undoTrouble.getWorry());
            etResolve.setText(undoTrouble.getResolve());
            adapter.updateCheckedItem(undoTrouble.getTenTypes());

            int mood = undoTrouble.getMood();
            setCheckedRadioButton(mood);
        }
    }

    /**
     * 插入全新记录或修改以前的记录
     *
     * @param worry   担心的问题
     * @param resolve 自我反驳
     * @param types   十大扭曲类型(可多选)
     * @param mood    当前心情
     * @return 是否添加成功
     */
    private boolean insertNewItem(String worry, String resolve, String types, int mood) {
        try {
            if (id > 0) {
                // 修改之前的记录
                Trouble trouble = new Trouble();
                trouble.setTimestamp(timeStamp);
                trouble.setWorry(worry);
                trouble.setResolve(resolve);
                trouble.setTenTypes(types);
                trouble.setMood(mood);
                trouble.update(id);
                return true;

            } else {
                // 添加全新的记录
                Trouble trouble = new Trouble();
                trouble.setTimestamp(timeStamp);
                trouble.setWorry(worry);
                trouble.setResolve(resolve);
                trouble.setTenTypes(types);
                trouble.setMood(mood);
                trouble.saveThrows();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 当前心情
     *
     * @param index 选项
     */
    private void setCheckedRadioButton(int index) {
        switch (index) {
            case 1:
                ((RadioButton) findViewById(R.id.rb_mood_1)).setChecked(true);
                break;
            case 2:
                ((RadioButton) findViewById(R.id.rb_mood_2)).setChecked(true);
                break;
            case 3:
                ((RadioButton) findViewById(R.id.rb_mood_3)).setChecked(true);
                break;
            case 4:
                ((RadioButton) findViewById(R.id.rb_mood_4)).setChecked(true);
                break;
            case 5:
                ((RadioButton) findViewById(R.id.rb_mood_5)).setChecked(true);
                break;
        }
    }
}
