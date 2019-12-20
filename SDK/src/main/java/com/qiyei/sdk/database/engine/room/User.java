package com.qiyei.sdk.database.engine.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Created by qiyei2015 on 2019/12/19.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Entity
public class User {

    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    /**
     * @return {@link #uid}
     */
    public int getUid() {
        return uid;
    }

    /**
     * @param uid the {@link #uid} to set
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * @return {@link #firstName}
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the {@link #firstName} to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return {@link #lastName}
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the {@link #lastName} to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
