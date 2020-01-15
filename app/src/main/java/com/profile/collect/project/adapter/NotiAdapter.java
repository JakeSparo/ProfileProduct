package com.profile.collect.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.profile.collect.project.entity.NotiEntity;
import com.profile.collect.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gouzhouping on 20-1-9.
 */

public class NotiAdapter extends BaseAdapter {

    private List<NotiEntity> mNotiEntities = new ArrayList<>();
    private Context mContext;

    public NotiAdapter(Context context, List<NotiEntity> notiEntities) {
        mContext = context;
        mNotiEntities = notiEntities;
    }

    @Override
    public int getCount() {
        return mNotiEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mNotiEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_noti_demo_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mButton = view.findViewById(R.id.noti_btn_item);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mButton.setText(mNotiEntities.get(position).getmNotiName());
        viewHolder.mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了：" + position + " Button ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    class ViewHolder {
        private Button mButton;
    }


}
