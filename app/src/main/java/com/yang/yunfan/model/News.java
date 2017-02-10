package com.yang.yunfan.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 新闻头条实体
 * Created by yang on 2017/1/7.
 */
@Entity
public class News implements Parcelable{


    /**
     * uniquekey : 2d82e5030069967e3037ecab13f31e7b
     * title : 航程11600公里 可载重77吨 已花58亿买10架 还想买
     * date : 2017-01-07 19:31
     * category : 头条
     * author_name : 浴火
     * url : http://mini.eastday.com/mobile/170107193134212.html
     * thumbnail_pic_s : http://05.imgmini.eastday.com/mobile/20170107/20170107193134_7426f341c65000852ae80c808f31d904_1_mwpm_03200403.jpeg
     * thumbnail_pic_s02 : http://05.imgmini.eastday.com/mobile/20170107/20170107193134_7426f341c65000852ae80c808f31d904_2_mwpm_03200403.jpeg
     * thumbnail_pic_s03 : http://05.imgmini.eastday.com/mobile/20170107/20170107193134_7426f341c65000852ae80c808f31d904_3_mwpm_03200403.jpeg
     */

    @Id
    private String uniquekey;
    private String title;
    private String date;
    private String category;
    private String author_name;
    private String url;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;

    @Generated(hash = 1741993752)
    public News(String uniquekey, String title, String date, String category, String author_name, String url, String thumbnail_pic_s,
            String thumbnail_pic_s02, String thumbnail_pic_s03) {
        this.uniquekey = uniquekey;
        this.title = title;
        this.date = date;
        this.category = category;
        this.author_name = author_name;
        this.url = url;
        this.thumbnail_pic_s = thumbnail_pic_s;
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }

    @Generated(hash = 1579685679)
    public News() {
    }

    protected News(Parcel in) {
        uniquekey = in.readString();
        title = in.readString();
        date = in.readString();
        category = in.readString();
        author_name = in.readString();
        url = in.readString();
        thumbnail_pic_s = in.readString();
        thumbnail_pic_s02 = in.readString();
        thumbnail_pic_s03 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uniquekey);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(category);
        dest.writeString(author_name);
        dest.writeString(url);
        dest.writeString(thumbnail_pic_s);
        dest.writeString(thumbnail_pic_s02);
        dest.writeString(thumbnail_pic_s03);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return thumbnail_pic_s03;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }
}
