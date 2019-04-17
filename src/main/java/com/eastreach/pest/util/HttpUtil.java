package com.eastreach.pest.util;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * http调用工具类
 * Map转URL查询字符串.
 */
public class HttpUtil extends RootUtil {

    private final static int SOCKET_TIMEOUT = 50000;
    private final static int CONNECTION_TIMEOUT = 10000;
    private final static int MAX_CONN_PRE_HOST = 40;
    private final static int MAX_CONN = 120;
    private static HttpClient httpClient;
    private final static HttpConnectionManager httpConnectionManager;

    static {
        httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = httpConnectionManager.getParams();
        params.setConnectionTimeout(CONNECTION_TIMEOUT);
        params.setSoTimeout(SOCKET_TIMEOUT);
        params.setDefaultMaxConnectionsPerHost(MAX_CONN_PRE_HOST);
        params.setMaxTotalConnections(MAX_CONN);
        httpClient = new HttpClient(httpConnectionManager);
    }

    /**
     * Map转化成URL查询字符串
     */
    public static String urlQueryString(Map<String, String> paramMap) {
        if (paramMap == null || paramMap.isEmpty()) {
            return "";
        }
        List<String> paramList = Lists.newArrayList();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            paramList.add(entry.getKey() + "=" + entry.getValue());
        }
        String requestString = "?" + Joiner.on("&").join(paramList);
        return requestString;
    }


    /**
     * GET请求
     *
     * @param url       //请求地址
     * @param headerMap //header内容
     * @param paramMap  //请求字符串
     * @return
     * @throws IOException
     */
    public static String get(String url, Map<String, String> headerMap, Map<String, String> paramMap) throws IOException {
        String requestUrl = url + urlQueryString(paramMap);
        GetMethod getMethod = new GetMethod(requestUrl);
        getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        getMethod.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            getMethod.addRequestHeader(entry.getKey(), entry.getValue());
        }
        logger.info(requestUrl);
        httpClient.executeMethod(getMethod);
        String responseString = getMethod.getResponseBodyAsString();
        logger.info(responseString);
        return responseString;
    }

    public static String get(String url, Map<String, String> paramMap) throws IOException {
        return get(url, Maps.<String, String>newConcurrentMap(), paramMap);
    }

    public static String get(String url) throws IOException {
        return get(url, Maps.<String, String>newConcurrentMap(), Maps.<String, String>newConcurrentMap());
    }

    /**
     * POST 请求
     *
     * @param url
     * @param headerMap
     * @param content
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> headerMap, String content) throws IOException {
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            postMethod.addRequestHeader(entry.getKey(), entry.getValue());
        }
        postMethod.setRequestBody(content);
        logger.info(url);
        logger.info(content);
        httpClient.executeMethod(postMethod);
        InputStream inputStream = postMethod.getResponseBodyAsStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        while ((temp = reader.readLine()) != null) {
            stringBuilder.append(temp);
        }
        String responseString = stringBuilder.toString();
        logger.info(responseString);
        return responseString;
    }

    public static String post(String url, String content) throws IOException {
        return post(url, Maps.<String, String>newConcurrentMap(), content);
    }

    public static JSONObject postJSON(String url, Map<String, String> headerMap, JSONObject requestJson) throws IOException {
        headerMap.put("content-type", "application/json");
        String responseString = post(url, headerMap, requestJson.toString());
        return JSONObject.fromObject(responseString);
    }

    public static JSONObject postJSON(String url, JSONObject requestJson) throws IOException {
        return postJSON(url, Maps.<String, String>newConcurrentMap(), requestJson);
    }

    public static Document postXML(String url, Map<String, String> headerMap, Document requestDoc) throws IOException, DocumentException {
        headerMap.put("Content-type", "text/xml;charset=utf-8");
        String responseString = post(url, headerMap, requestDoc.asXML());
        Document responseDoc = DocumentHelper.parseText(responseString);
        return responseDoc;
    }

    public static Document postXML(String url, Document requestDoc) throws IOException, DocumentException {
        return postXML(url, Maps.<String, String>newConcurrentMap(), requestDoc);
    }

    public static String postKV(String url, Map<String, String> headerMap, Map map) throws IOException, DocumentException {
        String queryString = createLinkString(map);
        headerMap.put("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return post(url, headerMap, queryString);
    }

    public static String postKV(String url, Map map) throws IOException, DocumentException {
        return postKV(url, Maps.<String, String>newConcurrentMap(), map);
    }


    /**
     * url参数字典排序
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String responseString = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {
                responseString = responseString + key + "=" + value;
            } else {
                responseString = responseString + key + "=" + value + "&";
            }
        }
        return responseString;
    }

    /**
     * 打印日志
     */
    public static void logRequestInfo(HttpServletRequest request) {
        logger.info("----HttpServletRequest---");
        logger.info("uri: " + request.getRequestURI());
        logger.info("method: " + request.getMethod());
        logger.info("queryString: " + request.getQueryString());
        logger.info("contentType: " + request.getContentType());
        logger.info("contentLength: " + request.getContentLength() + "");
        logger.info("----end------------------");
    }

}
