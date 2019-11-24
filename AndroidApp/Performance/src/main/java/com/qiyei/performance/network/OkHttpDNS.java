package com.qiyei.performance.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Dns;

/**
 * @author Created by qiyei2015 on 2019/11/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class OkHttpDNS implements Dns {

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        //使用阿里云或者根据域名写死ip
        return null;
    }

}
