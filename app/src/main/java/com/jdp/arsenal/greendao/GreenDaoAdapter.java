package com.jdp.arsenal.greendao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jdp.arsenal.R;

import java.util.List;

/**
 * Describe:
 * Author: jidp
 * Date: 2017/2/13
 */
public class GreenDaoAdapter extends RecyclerView.Adapter<GreenDaoAdapter.GreednDaoViewHolder> implements View.OnClickListener {

    private LayoutInflater mLayoutInflater;
    private List<User> mDataList;
    private OnDelete mOnDelete;

    public GreenDaoAdapter(Context ctx) {
        mLayoutInflater = LayoutInflater.from(ctx);
    }

    public void setDataList(List<User> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public void setOnDelete(OnDelete onDelete) {
        mOnDelete = onDelete;
    }

    public void add(User user) {
        if (mDataList != null) {
            mDataList.add(user);
            notifyItemInserted(mDataList.size());
        }
    }

    public void delete(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public GreednDaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GreednDaoViewHolder(mLayoutInflater.inflate(R.layout.item_greendao, parent, false));
    }

    @Override
    public void onBindViewHolder(GreednDaoViewHolder holder, int position) {
        holder.nameTv.setText(mDataList.get(position).getName() + " " + mDataList.get(position).getInfo());
        holder.deleBtn.setOnClickListener(this);
        holder.deleBtn.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_greendao_dele:
                if (mOnDelete != null) {
                    mOnDelete.onDelete((Integer) view.getTag());
                }
                break;
        }
    }

    public interface OnDelete {
        void onDelete(int position);
    }

    class GreednDaoViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv;
        Button deleBtn;

        public GreednDaoViewHolder(View itemView) {
            super(itemView);

            nameTv = (TextView) itemView.findViewById(R.id.item_greendao_name);
            deleBtn = (Button) itemView.findViewById(R.id.item_greendao_dele);
        }
    }
}
