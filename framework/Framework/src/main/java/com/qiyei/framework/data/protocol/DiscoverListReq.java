package com.qiyei.framework.data.protocol;

import com.google.gson.annotations.SerializedName;

/**
 * @author Created by qiyei2015 on 2017/10/25.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class DiscoverListReq extends BaseReq{

    @SerializedName("iid")
    private String iid;

    @SerializedName("aid")
    private String aid;

    public DiscoverListReq() {
        super();
    }

    /**
     * @return {@link #iid}
     */
    public String getIid() {
        return iid;
    }

    /**
     * @param iid the {@link #iid} to set
     */
    public void setIid(String iid) {
        this.iid = iid;
    }

    /**
     * @return {@link #aid}
     */
    public String getAid() {
        return aid;
    }

    /**
     * @param aid the {@link #aid} to set
     */
    public void setAid(String aid) {
        this.aid = aid;
    }
}
