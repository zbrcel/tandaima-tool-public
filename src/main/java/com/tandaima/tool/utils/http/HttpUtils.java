package com.tandaima.tool.utils.http;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zbrcel@gmail.com
 * @description httpclient
 */
public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);


    /**
     * get 传递对象
     * @param url url
     * @param param map
     */
    public static String doGet(String url, Map<String, Object> param) {
        return doGet(url,param,null,null);
    }

    /**
     * get 传递对象
     * @param url url
     * @param param map
     */
    public static String doGet(String url, Map<String, Object> param, Map<String, String> headerParam,RequestConfig requestConfig) {
        LOGGER.info("请求GET URL:{},请求参数:{}", url, JSONObject.toJSONString(param));
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, String.valueOf(param.get(key)));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            //设置配置
            httpGet.setConfig(defaultRequestConfig(requestConfig));
            // 创建参数列表
            if (headerParam != null) {
                for (String key : headerParam.keySet()) {
                    httpGet.setHeader(key,headerParam.get(key));
                }
            }
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            LOGGER.error("请求URL{},异常信息:{}",url,e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        LOGGER.info("返回数据:{}", resultString);
        return resultString;
    }

    /**
     * post 传递对象
     * @param url url
     * @param param map
     */
    public static String doPost(String url, Map<String, String> param) {
        LOGGER.info("请求POST URL:{},请求参数:{}", url,JSONObject.toJSONString(param));
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //设置配置
            httpPost.setConfig(defaultRequestConfig(null));
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"UTF-8");
                httpPost.setEntity(entity);
                httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            resultString = "error:"+e.getMessage();
            LOGGER.error("请求URL{},异常信息:{}",url,e.getMessage());
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        LOGGER.info("返回数据:{}", resultString);
        return resultString;
    }

    /**
     *  post 传递对象
     * @param url url
     * @param jsonSting jsonSting
     */
    public static String doPost(String url, String jsonSting) {
        LOGGER.info("请求POST URL:{},请求参数:{}", url,jsonSting);
        HttpPost postUrl = new HttpPost(url);
        postUrl.setHeader("Content-Type", "application/json;charset=utf8");
        //设置配置
        postUrl.setConfig(defaultRequestConfig(null));
        return httpPost(postUrl,jsonSting);
    }

    /**
     *  post 传递对象
     * @param url url
     * @param jsonSting jsonSting
     */
    public static String doPost(String url, String jsonSting,int timeout) {
        LOGGER.info("请求POST URL:{},请求参数:{}", url,jsonSting);
        HttpPost postUrl = new HttpPost(url);
        postUrl.setHeader("Content-Type", "application/json;charset=utf8");
        //设置配置
        postUrl.setConfig(getRequestConfig(timeout));
        return httpPost(postUrl,jsonSting);
    }

    /**
     * post 传递对象
     * @param url url
     * @param url headerParam
     * @param jsonSting jsonSting
     */
    public static String doPost(String url, String jsonSting, Map<String, String> headerParam){
        LOGGER.info("请求POST URL:{},请求参数:{}", url,jsonSting);
        HttpPost postUrl = new HttpPost(url);
        postUrl.setHeader("Content-Type", "application/json;charset=utf8");
        setHeader(postUrl,headerParam);
        //设置配置
        postUrl.setConfig(defaultRequestConfig(null));
        return httpPost(postUrl,jsonSting);
    }

    /**
     * post 传递对象
     * @param url url
     * @param url headerParam
     * @param jsonSting jsonSting
     */
    public static String doPost(String url, String jsonSting, Map<String, String> headerParam,RequestConfig requestConfig){
        LOGGER.info("请求POST URL:{},请求参数:{}", url,jsonSting);
        HttpPost postUrl = new HttpPost(url);
        postUrl.setHeader("Content-Type", "application/json;charset=utf8");
        setHeader(postUrl,headerParam);
        //设置配置
        postUrl.setConfig(defaultRequestConfig(requestConfig));
        return httpPost(postUrl,jsonSting);
    }

    private static String httpPost(HttpPost postUrl, String jsonSting){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        StringEntity entity = new StringEntity(jsonSting, "UTF-8");
        postUrl.setEntity(entity);
        // 响应模型
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(postUrl);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity);
            }
        }catch (Exception e) {
            result = "error:"+e.getMessage();
            LOGGER.error("请求URL{},异常信息:{}",postUrl.getURI(),e.getMessage());
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("返回数据:{}",result);
        return result;
    }

    /**
     * 设置请求头信息
     * @param postUrl HttpPost
     * @param headerParam Map
     */
    private static void setHeader(HttpPost postUrl,Map<String, String> headerParam){
        // 创建参数列表
        if (headerParam != null) {
            for (String key : headerParam.keySet()) {
                postUrl.setHeader(key,headerParam.get(key));
            }
        }
    }

    /**
     * post form模式
     * @param url 请求地址
     * @param param 参数MAP
     * @param headerParam 头部MAP
     */
    public static String doPostForm(String url,Map<String, Object> param,Map<String, String> headerParam) {
        LOGGER.info("请求POSTForm URL:{},请求参数:{}", url, JSON.toJSONString(param));
        HttpPost postUrl = new HttpPost(url);
        postUrl.setHeader("Content-Type", "application/json;charset=utf8");
        setHeader(postUrl,headerParam);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //设置配置
        postUrl.setConfig(defaultRequestConfig(null));
        List<BasicNameValuePair> pairList = new ArrayList<>();
        for (String key : param.keySet()) {
            pairList.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
        }
        // 响应模型
        CloseableHttpResponse response = null;
        String result = "";
        try {
            postUrl.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(postUrl);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity);
            }
        }catch (Exception e) {
            result = "error:"+e.getMessage();
            LOGGER.error("请求URL{},异常信息:{}",url,e.getMessage());
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("返回数据:{}",result);
        return result;
    }


    /**
     * 获取form表单提交头部配置
     * @return 配置
     */
    public static Map<String,String> getFormHeader(){
        Map<String,String> headMap = new HashMap<>();
        headMap.put("Content-Type","application/x-www-form-urlencoded");
        return headMap;
    }

    /**
     * 设置请求配置 默认配置
     */
    private static RequestConfig defaultRequestConfig(RequestConfig requestConfig){
        return requestConfig == null ? RequestConfig.custom()
                //设置连接超时时间，单位毫秒。
                .setConnectTimeout(5000)
                //设置从connect Manager获取Connection 超时时间，单位毫秒
                .setConnectionRequestTimeout(1000)
                //请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
                //5s未响应直接放弃
                .setSocketTimeout(5000).build(): requestConfig;
    }
    /**
     * 设置请求配置
     */
    private static RequestConfig getRequestConfig(Integer timeout){
        if(timeout == null){
            return defaultRequestConfig(null);
        }
        return  RequestConfig.custom()
                //设置连接超时时间，单位毫秒。
                .setConnectTimeout(timeout)
                //设置从connect Manager获取Connection 超时时间，单位毫秒
                .setConnectionRequestTimeout(timeout)
                //请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
                //5s未响应直接放弃
                .setSocketTimeout(timeout).build();
    }
}
