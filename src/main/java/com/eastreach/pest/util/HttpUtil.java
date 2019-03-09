package com.eastreach.pest.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * http调用工具类
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    // 读取超时
    private final static int SOCKET_TIMEOUT = 50000;
    // 连接超时
    private final static int CONNECTION_TIMEOUT = 10000;
    // 每个HOST的最大连接数量
    private final static int MAX_CONN_PRE_HOST = 40;
    // 连接池的最大连接数
    private final static int MAX_CONN = 120;
    // 连接池
    private final static HttpConnectionManager httpConnectionManager;
    private static HttpClient httpClient;

    static {
        httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = httpConnectionManager.getParams();
        params.setConnectionTimeout(CONNECTION_TIMEOUT);
        params.setSoTimeout(SOCKET_TIMEOUT);
        params.setDefaultMaxConnectionsPerHost(MAX_CONN_PRE_HOST);
        params.setMaxTotalConnections(MAX_CONN);
        httpClient = new HttpClient(httpConnectionManager);
    }

    private static HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * 向指定地址post数据
     */
    public static String get(String url, Map<String,String> paramMap) throws IOException {
        //请求参数拼接
        List<String> paramList = Lists.newArrayList();
        if (paramMap != null && paramMap.size() > 0) {
            for (Map.Entry<String, String> e : paramMap.entrySet()) {
                paramList.add(e.getKey() + "=" + e.getValue());
            }
        }
        String requestString = Joiner.on("&").join(paramList);

        //get接口请求调用
        String requestUrl = url+"?"+requestString;
        GetMethod getMethod = new GetMethod(requestUrl);
        httpClient.executeMethod(getMethod);
        String response = getMethod.getResponseBodyAsString();
        return response;
    }

    public static  String post(String url, Map<String,String> paramMap) throws IOException {
        //请求参数拼接
        List<String> paramList = Lists.newArrayList();
        if (paramMap != null && paramMap.size() > 0) {
            for (Map.Entry<String, String> e : paramMap.entrySet()) {
                paramList.add(e.getKey() + "=" + e.getValue());
            }
        }
        String requestString = Joiner.on("&").join(paramList);

        //post接口调用
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        postMethod.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        postMethod.setRequestBody(requestString.toString());
        httpClient.executeMethod(postMethod);
        InputStream inputStream = postMethod.getResponseBodyAsStream();

        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = inputStream.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        String response = out.toString();
        postMethod.releaseConnection();

        logger.info(url + ":" + requestString.toString());
        logger.info(response);
        return response;
    }
}
