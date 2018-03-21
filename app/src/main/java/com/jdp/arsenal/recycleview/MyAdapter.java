package com.jdp.arsenal.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jdp.arsenal.R;

import java.util.List;

/**
 * Describe:
 * Author: jidp
 * Date: 2017/2/7
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mCtx;
    private List<String> mDataList;

    public MyAdapter(Context ctx) {
        mCtx = ctx;
    }

    public void add(String str) {
        mDataList.add(mDataList.size() - 1, str);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void setDataList(List<String> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == 1) {
            holder = new ViewHolder1(LayoutInflater.from(mCtx).inflate(R.layout.item_recycle_view_1, parent, false));
        } else {
            holder = new ViewHolder2(LayoutInflater.from(mCtx).inflate(R.layout.item_recycle_view_2, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1) {
            ((ViewHolder1) holder).chk.setText(mDataList.get(position));
        } else if (holder instanceof ViewHolder2) {
            ((ViewHolder2) holder).tv.setText(mDataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3 == 0 ? 1 : 2;
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        CheckBox chk;

        public ViewHolder1(View itemView) {
            super(itemView);

            chk = (CheckBox) itemView.findViewById(R.id.item_chk);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView tv;

        public ViewHolder2(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
