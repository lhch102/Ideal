package com.lhch.ideal.db;

import org.litepal.crud.DataSupport;

/**
 * 榜单实体
 * Created by Administrator on 2017/10/3.
 */

public class TopList extends DataSupport {

    //榜单名称
    private String topName;
    //榜单图片ID
    private  int imageId;
    //榜单海报
    private  int imagePic;
    //榜单评分
    private  float topScore;


    public TopList(String topName, float topScore) {
        this.topName = topName;
        this.topScore = topScore;
    }

    public TopList(String topName, int imageId) {
        this.topName = topName;
        this.imageId = imageId;
    }

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImagePic() {
        return imagePic;
    }

    public void setImagePic(int imagePic) {
        this.imagePic = imagePic;
    }

    public float getTopScore() {
        return topScore;
    }

    public void setTopScore(float topScore) {
        this.topScore = topScore;
    }
}
