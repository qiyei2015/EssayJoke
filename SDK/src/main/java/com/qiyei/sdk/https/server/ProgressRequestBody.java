package com.qiyei.sdk.https.server;



import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * @author Created by qiyei2015 on 2017/10/29.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ProgressRequestBody extends RequestBody {

    /**
     * 带包装的实体
     */
    private RequestBody mRequestBody;

    /**
     * 传输的回调
     */
    private final IHttpTransferCallback mCallback;

    /**
     * 包装完成的BufferedSink
     */
    private BufferedSink mBufferedSink;

    public ProgressRequestBody(IHttpTransferCallback callback) {
        mCallback = callback;
    }

    /**
     * 构造方法
     * @param requestBody
     * @param callback
     */
    public ProgressRequestBody(RequestBody requestBody, IHttpTransferCallback callback) {
        mRequestBody = requestBody;
        mCallback = callback;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mBufferedSink == null){
            mBufferedSink = Okio.buffer(sink(sink));
        }
        //请求写入
        mRequestBody.writeTo(mBufferedSink);
        //刷新
        mBufferedSink.flush();
    }


    /**
     * 写入，回调进度接口
     * @param sink
     * @return
     */
    private Sink sink(Sink sink){
        return new ForwardingSink(sink) {
            /**
             * 已写入字节数
             */
            private long byteWritten = 0L;
            /**
             * 总字节数
             */
            private long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0){
                    contentLength = mRequestBody.contentLength();
                }
                byteWritten += byteCount;
                //回调callBack
                mCallback.onProgress(byteWritten,contentLength);
            }
        };
    }

    /**
     * @param requestBody the {@link #mRequestBody} to set
     */
    public void setRequestBody(RequestBody requestBody) {
        mRequestBody = requestBody;
    }
}
