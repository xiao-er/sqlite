package com.xiaoxiao.sqlite.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: 潇潇
 * @create on:  2019/12/10
 * @describe:DOTO
 */

public class BookData implements Parcelable {
    private int id;
    private String name;
    private String createDate;
    private String editDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.createDate);
        dest.writeString(this.editDate);
    }

    public BookData() {
    }

    protected BookData(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.createDate = in.readString();
        this.editDate = in.readString();
    }

    public static final Creator<BookData> CREATOR = new Creator<BookData>() {
        @Override
        public BookData createFromParcel(Parcel source) {
            return new BookData(source);
        }

        @Override
        public BookData[] newArray(int size) {
            return new BookData[size];
        }
    };
}
