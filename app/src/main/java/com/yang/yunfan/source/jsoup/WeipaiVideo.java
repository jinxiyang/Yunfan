package com.yang.yunfan.source.jsoup;

import android.os.Parcel;
import android.os.Parcelable;

public class WeipaiVideo implements Parcelable{
    private String videoHtmlUrl;
    private String title;
    private String imageUrl;
    private String userIconUrl;
    private String userName;


    protected WeipaiVideo(Parcel in) {
        videoHtmlUrl = in.readString();
        title = in.readString();
        imageUrl = in.readString();
        userIconUrl = in.readString();
        userName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoHtmlUrl);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(userIconUrl);
        dest.writeString(userName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeipaiVideo> CREATOR = new Creator<WeipaiVideo>() {
        @Override
        public WeipaiVideo createFromParcel(Parcel in) {
            return new WeipaiVideo(in);
        }

        @Override
        public WeipaiVideo[] newArray(int size) {
            return new WeipaiVideo[size];
        }
    };

    public String getVideoHtmlUrl() {
        return videoHtmlUrl;
    }

    public void setVideoHtmlUrl(String videoHtmlUrl) {
        this.videoHtmlUrl = videoHtmlUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public WeipaiVideo() {
        super();
    }
}
