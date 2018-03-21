package com.jdp.arsenal.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jdp.arsenal.MyApplication;
import com.jdp.arsenal.R;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class GreenDaoActivity extends Activity implements View.OnClickListener {

    private PtrClassicFrameLayout mPtrLayout;
    private RecyclerView mRecyclerView;
    private Button mInsert;

    private UserDao mUserDao;
    private List<User> mDataList;
    private GreenDaoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        initView();
        initListener();
        initData();
    }

    private void initData() {
        mUserDao = MyApplication.getInstance().getDaoSession().getUserDao();
        mAdapter.setDataList(mDataList = query());
        mAdapter.setOnDelete(new GreenDaoAdapter.OnDelete() {
            @Override
            public void onDelete(int position) {
                mUserDao.delete(mDataList.get(position));
                mAdapter.delete(position);
            }
        });
    }

    private void initListener() {
        mInsert.setOnClickListener(this);
        mPtrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPtrLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrLayout.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrLayout.refreshComplete();
                        mAdapter.setDataList(mDataList = query());
                    }
                }, 1000);
            }
        });
    }

    private void initView() {
        mPtrLayout = (PtrClassicFrameLayout) findViewById(R.id.greendao_ptr_layout);
        mInsert = (Button) findViewById(R.id.insert_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.greendao_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new GreenDaoAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<User> query() {
        return mUserDao.queryBuilder().list();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert_btn:
                User user = new User();
                user.setName(System.currentTimeMillis() + "");
                mUserDao.insert(user);
                mAdapter.add(user);
                break;
        }
    }
}
