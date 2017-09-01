package com.qiyei.sdk.server;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/31.
 * Version: 1.0
 * Description: Clien端与Server端传递消息的实体
 */
public class BMessage implements Parcelable{

    /**
     * id
     */
    public int clientId;
    /**
     * 消息
     */
    public String msg;

    public BMessage() {
    }

    protected BMessage(Parcel in) {
        this.clientId = in.readInt();
        this.msg = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.clientId);
        dest.writeString(this.msg);
    }

    public static final Creator<BMessage> CREATOR = new Creator<BMessage>() {
        @Override
        public BMessage createFromParcel(Parcel source) {
            return new BMessage(source);
        }

        @Override
        public BMessage[] newArray(int size) {
            return new BMessage[size];
        }
    };
}
