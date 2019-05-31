package com.luzuzu.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fula on 2019/5/16.
 */

public class VideoBean implements Parcelable{

    public VideoBean(String title,String thumb, String url,String adUrl) {
        this.title = title;
        this.thumb = thumb;
        this.url = url;
        this.adUrl=adUrl;
    }

    private String title;
    private String url;

    protected VideoBean(Parcel in) {
        title = in.readString();
        url = in.readString();
        adUrl = in.readString();
        thumb = in.readString();
    }

    public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel in) {
            return new VideoBean(in);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    private String adUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    private String thumb;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(adUrl);
        dest.writeString(thumb);
    }
}
