package com.lhch.ideal.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * 影片实体类
 * Created by Administrator on 2017/10/2.
 */

public class MovieInfo extends DataSupport {

    //影片ID
    private int id;
    //影片中文名
    private String chineseName;
    //英文名
    private String englishName;
    //别名
    private String ask;
    //竖图海报
    private String images;
    //影片评分
    private float grade;
    //制片国家
    private String country;
    //年代
    private int filmYears;
    //影片类型
    private char filmTypes;
    //简介
    private String introduction;
    //影片标签
    private String filmLabel;
    //影单ID
    private int movieListId;
    //影片来源；0:豆瓣；1:IMDB；2:letterboxd；3:烂番茄；4:Netflix；5:Facebook；
    private char filmSource;
    //媒体类型；0：电影；1：电视剧；2：动漫；3：纪录片；4：纪录片；5：综艺节目
    private char mediumType;
    //时长
    private int filmTime;
    //影片热度
    private int head;
    //创建人
    private int createName;
    //创建时间
    private Date createTime;
    //修改人
    private int modifyName;
    //修改时间
    private Date modifyTime;

    private int imagesId;

    public MovieInfo(String chineseName, int imagesId) {
        this.chineseName = chineseName;
        this.imagesId = imagesId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getFilmYears() {
        return filmYears;
    }

    public void setFilmYears(int filmYears) {
        this.filmYears = filmYears;
    }

    public char getFilmTypes() {
        return filmTypes;
    }

    public void setFilmTypes(char filmTypes) {
        this.filmTypes = filmTypes;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getFilmLabel() {
        return filmLabel;
    }

    public void setFilmLabel(String filmLabel) {
        this.filmLabel = filmLabel;
    }

    public int getMovieListId() {
        return movieListId;
    }

    public void setMovieListId(int movieListId) {
        this.movieListId = movieListId;
    }

    public char getFilmSource() {
        return filmSource;
    }

    public void setFilmSource(char filmSource) {
        this.filmSource = filmSource;
    }

    public char getMediumType() {
        return mediumType;
    }

    public void setMediumType(char mediumType) {
        this.mediumType = mediumType;
    }

    public int getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(int filmTime) {
        this.filmTime = filmTime;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getCreateName() {
        return createName;
    }

    public void setCreateName(int createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getModifyName() {
        return modifyName;
    }

    public void setModifyName(int modifyName) {
        this.modifyName = modifyName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getImagesId() {
        return imagesId;
    }

    public void setImagesId(int imagesId) {
        this.imagesId = imagesId;
    }
}
