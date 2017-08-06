package com.xinbob.curable.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xinbob.curable.R;
import com.xinbob.curable.db.Trouble;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinbob on 7/17/17.
 * 烦恼担心的列表
 */

public class TroublesListAdapter extends BaseAdapter {

    private Context context;
    private List<Trouble> datas;
    private LayoutInflater inflater;

    public TroublesListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void updateDatas(List<Trouble> newDatas) {
        if (datas != null) {
            datas.clear();
        }
        datas = new ArrayList<>(newDatas);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_item_troubles_list, parent, false);
            holder = new ViewHolder();
            holder.ivIcon = convertView.findViewById(R.id.iv_trouble_icon);
            holder.tvContent = convertView.findViewById(R.id.tv_trouble_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Trouble t = datas.get(position);

        if (!TextUtils.isEmpty(t.getResolve()) &&
                !TextUtils.isEmpty(t.getTenTypes()) && t.getMood() >= 0) {
            holder.ivIcon.setImageResource(R.drawable.icon_done);
        } else {
            holder.ivIcon.setImageResource(R.drawable.icon_undo);
        }

        holder.tvContent.setText(t.getWorry());
        return convertView;
    }

    private class ViewHolder {
        ImageView ivIcon;
        TextView tvContent;
    }
}
