package com.profile.collect.project.entity;

/**
 * Created by gouzhouping on 20-1-8.
 */

public class BrowseEntity {

    public int drawableId;
    public String name;

    public BrowseEntity(int drawableId, String name) {
        this.drawableId = drawableId;
        this.name = name;
    }


    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BrowseEntity{" +
                "drawableId=" + drawableId +
                ", name='" + name + '\'' +
                '}';
    }
}
