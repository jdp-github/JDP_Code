package com.jdp.arsenal.recycleview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jdp.arsenal.R;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class RecycleViewActivity extends Activity {

    private PtrClassicFrameLayout mFrameLayout;
    private RecyclerView mRecyclerView;
    private List<String> mDataList;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        mFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.collection_ptr_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataList(mDataList);

        mFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.add("hhh");
                        mFrameLayout.refreshComplete();
                        mRecyclerView.scrollToPosition(mDataList.size() - 1);
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFrameLayout.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    protected void initData() {
        mDataList = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDataList.add("" + (char) i);
        }
    }
}
