package com.xinbob.curable.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xinbob.curable.R;
import com.xinbob.curable.adapter.TroublesListAdapter;
import com.xinbob.curable.base.BaseActivity;
import com.xinbob.curable.db.Trouble;
import com.xinbob.curable.utils.AlarmSettingUtils;
import com.xinbob.curable.utils.Constants;
import com.xinbob.curable.utils.TimeUtils;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 主Activity
 */

public class MainActivity extends BaseActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    private ListView listView;
    private TroublesListAdapter adapter;
    private List<Trouble> datas;

    private String now; // yyyy-MM-dd

    @Override
    public int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public int setOnCreateOptionsMenuResId() {
        return R.menu.menu_main;
    }

    @Override
    public void onOptionsItemSelectedHandler(MenuItem item) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            refreshTodayWorryDisplayOnListView();
            recomposeToolbar(false, getResources().getString(R.string.app_name) + " " +
                    TimeUtils.replaceDashInDateStr(now, false));
        }

        if (item.getItemId() == R.id.menu_choose_date) {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    MainActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.vibrate(false);
            dpd.show(getFragmentManager(), "Datepickerdialog");
        }
        return true;
    }

    @Override
    public void preInitViews() {
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        now = sdf.format(new Date());

        AlarmSettingUtils.setCheckTodayRecordSchedule(this, Constants.HOUR_OF_DAY, Constants.MINUTES);
    }


    @Override
    public void initViews() {
        recomposeToolbar(false, getResources().getString(R.string.app_name) + " " +
                TimeUtils.replaceDashInDateStr(now, false));
        findViewById(R.id.fab_add_new_item).setOnClickListener(this);

        listView = (ListView) findViewById(R.id.lv_content);
        adapter = new TroublesListAdapter(this);
        listView.setAdapter(adapter);

        // 长按Item删除该项
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (datas != null && datas.size() > 0) {
                    if (datas.get(pos).getTimestamp().equals(now)) {
                        int itemId = datas.get(pos).getId();
                        showConfirmToDelete(itemId, "删除该项吗?");
                    } else {
                        Toast.makeText(MainActivity.this, "哎呀!之前的记录不能修改", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        // 点击Item继续记录或者查看之前记录的内容
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (datas != null && datas.size() > 0) {
                    final Trouble trouble = datas.get(pos);

                    if (trouble.getTimestamp().equals(now)) {
                        if (!TextUtils.isEmpty(trouble.getResolve()) &&
                                !TextUtils.isEmpty(trouble.getTenTypes()) && trouble.getMood() >= 0) {
                            // 已完成 仅仅展示
                            showCheckDoneItemDialog(trouble.getWorry(), trouble.getResolve(), trouble.getTenTypes());
                        } else {
                            Intent intent = new Intent(MainActivity.this, AddWorryActivity.class);
                            intent.putExtra("undo", trouble);
                            startActivity(intent);
                        }
                    } else {
                        // 不是当天的 只能查看
                        showCheckDoneItemDialog(trouble.getWorry(), null, null);
                    }

                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTodayWorryDisplayOnListView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_new_item:
                startActivity(new Intent(MainActivity.this, AddWorryActivity.class));

                // 打赏开发者测试
                // startActivity(new Intent(MainActivity.this, TestActivity.class));
                break;
        }
    }

    /**
     * 刷新Toolbar
     *
     * @param isDisplayHomeAsUp 是否显示默认返回键
     */
    private void recomposeToolbar(boolean isDisplayHomeAsUp, String title) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUp);
            supportActionBar.setTitle(title);
        }
    }

    /**
     * 弹出Dialog展示已填写完成的Item
     *
     * @param worry      担心内容
     * @param contradict 自我反驳
     * @param tenTypes   十大扭曲
     */
    private void showCheckDoneItemDialog(String worry, String contradict, String tenTypes) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_show_detail, null);
        ((TextView) dialogView.findViewById(R.id.tv_your_worry)).setText(worry);
        ((TextView) dialogView.findViewById(R.id.tv_your_contradict)).setText(contradict);
        ((TextView) dialogView.findViewById(R.id.tv_ten_types)).setText(tenTypes);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setView(dialogView)
                .show();
    }

    /**
     * 弹出Snackbar是否确认删除该条目
     * 只能删除本日的item
     *
     * @param id      数据库默认生成id
     * @param content Snackbar显示警告内容
     */
    private void showConfirmToDelete(final int id, String content) {
        Snackbar.make(listView, content, Snackbar.LENGTH_SHORT)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataSupport.delete(Trouble.class, id);
                        refreshTodayWorryDisplayOnListView();
                    }
                })
                .show();
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        String yy = String.valueOf(year);
        String mm = String.valueOf(month + 1).length() == 1 ?
                "0" + String.valueOf(month + 1) : String.valueOf(month + 1);
        String dd = String.valueOf(day).length() == 1 ?
                "0" + String.valueOf(day) : String.valueOf(day);
        String dateChose = yy + "-" + mm + "-" + dd;
        queryHistoryWorryDisplayOnListView(dateChose);
    }

    @Override
    public void onBackPressed() {
        if (datas != null && datas.size() > 0) {
            if (!datas.get(0).getTimestamp().equals(now)) {
                // 不等于现在的话 就是返回本日记录
                refreshTodayWorryDisplayOnListView();
                recomposeToolbar(false, getResources().getString(R.string.app_name) + " " +
                        TimeUtils.replaceDashInDateStr(now, false));
            } else if (datas.get(0).getTimestamp().equals(now)) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 显示本日记录
     */
    private void refreshTodayWorryDisplayOnListView() {
        if (datas != null) {
            datas.clear();
        }
        datas = DataSupport.where("timestamp=?", now).find(Trouble.class);
        adapter.updateDatas(datas);
        findViewById(R.id.fab_add_new_item).setVisibility(View.VISIBLE);
    }

    /**
     * 查询目标日期记录
     *
     * @param dateChose yyyy-MM-dd
     */
    private void queryHistoryWorryDisplayOnListView(String dateChose) {
        List<Trouble> troubles = DataSupport.where("timestamp=?", dateChose).find(Trouble.class);
        if (troubles != null && troubles.size() > 0) {
            if (datas != null) {
                datas.clear();
            }
            datas = new ArrayList<>(troubles);
            adapter.updateDatas(datas);

            if (!troubles.get(0).getTimestamp().equals(now)) {
                // 显示返回按钮
                recomposeToolbar(true, TimeUtils.replaceDashInDateStr(dateChose, true));
                findViewById(R.id.fab_add_new_item).setVisibility(View.GONE);
            } else {
                recomposeToolbar(false, getResources().getString(R.string.app_name) + " " +
                        TimeUtils.replaceDashInDateStr(now, false));
            }
        } else {
            Toast.makeText(this, "未查询到该日的日期", Toast.LENGTH_SHORT).show();
        }
    }

}
