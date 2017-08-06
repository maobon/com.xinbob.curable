package com.xinbob.curable.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.xinbob.curable.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xinbob on 7/17/17.
 * 十大扭曲选项列表
 */

public class TenTypesAdapter extends RecyclerView.Adapter<TenTypesAdapter.ViewHolder> {

    private Context context;
    private List<String> checkedTypes = new ArrayList<>();

    private boolean[] isDefaultChecked = new boolean[Constants.TEN_TYPES.length];

    public String getCheckedTypes() {
        if (checkedTypes.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String str : checkedTypes) {
                sb.append(str).append("#");
            }

            String res = sb.toString();
            res = res.substring(0, res.length() - 1);

            Log.i("TAG", res);

            return res;
        }
        return null;
    }

    public TenTypesAdapter(Context context) {
        this.context = context;
    }

    /**
     * 更新选中的十大扭曲item
     *
     * @param s 选中的集合
     */
    public void updateCheckedItem(String s) {
        if (s == null) return;

        if (s.contains("#")) {
            String[] split = s.split("#");
            for (String checkedItem : split) {
                for (int i = 0; i < Constants.TEN_TYPES.length; i++) {
                    if (checkedItem.equals(Constants.TEN_TYPES[i])) {
                        isDefaultChecked[i] = true;
                        checkedTypes.add(Constants.TEN_TYPES[i]);
                    }
                }
            }
        } else {
            for (int i = 0; i < Constants.TEN_TYPES.length; i++) {
                if (s.equals(Constants.TEN_TYPES[i])) {
                    isDefaultChecked[i] = true;
                    checkedTypes.add(Constants.TEN_TYPES[i]);
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AppCompatCheckBox checkBox = new AppCompatCheckBox(context);
        checkBox.setPadding(0, 30, 0, 30);
        checkBox.setTextSize(13);
        checkBox.setGravity(Gravity.CENTER);
        checkBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        checkBox.setTextColor(Color.parseColor("#767676"));
        return new ViewHolder(checkBox);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindDatas(Constants.TEN_TYPES[position], position, isDefaultChecked[position]);
    }

    @Override
    public int getItemCount() {
        return Constants.TEN_TYPES.length;
    }

    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder
            implements CheckBox.OnCheckedChangeListener {

        private CheckBox checkBox;
        private int pos;

        ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView;
            checkBox.setOnCheckedChangeListener(this);
        }

        private void bindDatas(String str, int pos, boolean isChecked) {
            checkBox.setText(str);
            checkBox.setChecked(isChecked);
            this.pos = pos;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (checkBox.isPressed()) {
                if (isChecked) {
                    checkedTypes.add(Constants.TEN_TYPES[pos]);
                    isDefaultChecked[pos] = true;
                } else {
                    checkedTypes.remove(Constants.TEN_TYPES[pos]);
                    isDefaultChecked[pos] = false;
                }
            }
        }
    }

}
