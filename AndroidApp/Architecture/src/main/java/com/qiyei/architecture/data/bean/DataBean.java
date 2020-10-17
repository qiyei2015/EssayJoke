package com.qiyei.architecture.data.bean;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.qiyei.architecture.BR;

/**
 * @author Created by qiyei2015 on 2017/11/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */

public class DataBean extends BaseObservable {

    private long id;
    private String name;
    private String value;

    public DataBean(long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public DataBean() {
    }

    @Bindable
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }

    public interface ${
        String id = "id";
        String name = "name";
        String value = "value";
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
