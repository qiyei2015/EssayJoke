package com.qiyei.sdk.https.base;


import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/29.
 * Version: 1.0
 * Description:
 */
public class HttpHelper {

    /**
     * 创建get请求的url
     * @param request
     * @return
     */
    public static String buildGetRequest(HttpRequest request){
        String url = request.getUrl();

        Map<String,Object> params = request.getParams();
        if (params == null || (params!=null && params.size() < 0)){
            return url;
        }

        StringBuffer buffer = new StringBuffer(url);

        if (!url.contains("?")){
            buffer.append("?");
        }else {
            if (!url.endsWith("?")){
                buffer.append("&");
            }
        }

        for (Map.Entry<String,Object> entity : params.entrySet()){
            buffer.append(entity.getKey()).append("=").append(entity.getValue()).append("&");
        }

        buffer.deleteCharAt(buffer.length() - 1);

        return buffer.toString();
    }


}
