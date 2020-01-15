package com.profile.collect.project.entity;

/**
 * Created by gouzhouping on 20-1-9.
 */

public class NotiEntity {

    private String mNotiName;

    public NotiEntity(String mNotiName) {
        this.mNotiName = mNotiName;
    }

    public String getmNotiName() {
        return mNotiName;
    }

    public void setmNotiName(String mNotiName) {
        this.mNotiName = mNotiName;
    }

    @Override
    public String toString() {
        return "NotiEntity{" +
                "mNotiName='" + mNotiName + '\'' +
                '}';
    }
}
