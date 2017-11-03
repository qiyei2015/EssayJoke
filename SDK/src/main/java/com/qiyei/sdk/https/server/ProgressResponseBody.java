package com.qiyei.sdk.https.server;

import android.support.annotation.Nullable;

import java.io.IOException;


import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

import okio.Source;


/**
 * @author Created by qiyei2015 on 2017/10/29.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: okHttp下载进度body
 */
public class ProgressResponseBody extends ResponseBody{

    /**
     * 带包装的实体
     */
    private ResponseBody mResponseBody;

    /**
     * 传输的回调
     */
    private final IHttpTransferCallback mCallback;

    /**
     * 包装完成的BufferedSource
     */
    private BufferedSource mBufferedSource;

    public ProgressResponseBody(IHttpTransferCallback callback) {
        mCallback = callback;
        mResponseBody = null;
    }

    /**
     * 构造方法
     * @param responseBody
     * @param callback
     */
    public ProgressResponseBody(ResponseBody responseBody, IHttpTransferCallback callback) {
        mResponseBody = responseBody;
        mCallback = callback;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }


    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    @Override
    public void close() {
        if (mBufferedSource != null){
            try {
                mBufferedSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取，回调进度接口
     * @param source
     * @return
     */
    private Source source(Source source){
        return new ForwardingSource(source) {
            /**
             * 已经读取的总字节数
             */
            long totalRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                // 获取已读字节数
                long byteRead = super.read(sink, byteCount);
                // 增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalRead += byteRead != -1 ? byteRead : 0;
                // 回调，如果contentLength()不知道长度，会返回-1
                mCallback.onProgress(totalRead,mResponseBody.contentLength());
                return byteRead;
            }
        };
    }

    /**
     * @param responseBody the {@link #mResponseBody} to set
     */
    public void setResponseBody(ResponseBody responseBody) {
        mResponseBody = responseBody;
    }
}
