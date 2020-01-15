package com.profile.collect.project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.profile.collect.project.entity.NotiEntity;
import com.profile.collect.project.adapter.NotiAdapter;
import com.profile.collect.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gouzhouping on 20-1-9.
 */

public class NotiActivity extends Activity {

    private ListView mList;
    private NotiAdapter mNotiAdapter;
    private List<NotiEntity> mNotiEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_noti_demo);
        mList = (ListView) findViewById(R.id.noti_list);
        mNotiEntity = new ArrayList<>();
        // 初始化资源
        initData();
    }

    private void initData() {

        String[] notiNames = getResources().getStringArray(R.array.noti_name);

        if (!mNotiEntity.isEmpty()) {
            mNotiEntity.clear();
        }

        for (String name : notiNames) {
            mNotiEntity.add(new NotiEntity(name));
        }

        mNotiAdapter = new NotiAdapter(this, mNotiEntity);
        mList.setAdapter(mNotiAdapter);

        // 点击事件处理
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "点击了: " + position + " Item ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
