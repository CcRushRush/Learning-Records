```java
package com.lango.onenet_server.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * jar包依赖
 * <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpclient</artifactId>
 * <version>4.5.12</version>
 * </dependency>
 * <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpmime -->
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpmime</artifactId>
 * <version>4.5.12</version>
 * </dependency>
 * <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore -->
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpcore</artifactId>
 * <version>4.4.13</version>
 * </dependency>
 * <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
 * <dependency>
 * <groupId>com.fasterxml.jackson.core</groupId>
 * <artifactId>jackson-core</artifactId>
 * <version>2.11.0</version>
 * </dependency>
 * <!-- https://mvnrepository.com/artifact/org.codehaus.jackson/jackson-mapper-asl -->
 * <dependency>
 * <groupId>org.codehaus.jackson</groupId>
 * <artifactId>jackson-mapper-asl</artifactId>
 * <version>1.9.13</version>
 * </dependency>
 */

/**
 * @author cc
 * @version 1.0
 * @date 2020/12/10 18:24
 */
public class HttpClientUtil {

    public static final String POST_CLIENT = "POST";
    public static final String GET_CLIENT = "GET";
    public static final String DELETE_CLIENT = "DELETE";
    public static final String PUT_CLIENT = "PUT";

    public static String httpClient(String url, String method, Map<String, String> params, Map<String, String> headers, String token) {
        String result;
        switch (method) {
            case POST_CLIENT:
                result = httpPostClient(url, params, headers, token);
                break;
            case GET_CLIENT:
                result = httpGetClient(url, params, headers, token);
                break;
            case DELETE_CLIENT:
                result = httpDeleteClient(url, params, headers, token);
                break;
            case PUT_CLIENT:
                result = httpPutClient(url, params, headers, token);
                break;
            default:
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 200);
                jsonObject.put("msg", "没有对应的请求方法");
                result = jsonObject.toJSONString();
        }
        return result;
    }

    /**
     * post请求
     */
    public static String httpPostClient(String url, Map<String, String> params, Map<String, String> headers, String token) {
        HttpClient httpclient = HttpClientBuilder.create().build();
        try {
            // 2：创建http的发送方式对象，是GET还是post
            HttpPost httppost = new HttpPost(url);

            // 3：创建要发送的实体，就是key-value的这种结构，借助于这个类，可以实现文件和参数同时上传，很简单的。
            JSONObject jsonObject = new JSONObject();
            if (params != null && !params.isEmpty()) {
                for (String key : params.keySet()) {
                    jsonObject.put(key, params.get(key));
                }
                StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");//解决中文乱码问题
                httppost.setEntity(entity);
            }
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    httppost.setHeader(key, headers.get(key));
                }
            }
            if (token != null && !token.isEmpty()) {
                httppost.setHeader("authorization", token);
            }
            // 4：执行httpPost对象，从而获得信息
            HttpResponse response = httpclient.execute(httppost);
            org.apache.http.HttpEntity resEntity = response.getEntity();

            // 获得返回来的信息，转化为字符串string
            return EntityUtils.toString(resEntity);
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            }
        }
        return "{}";
    }

    /**
     * get请求
     */
    public static String httpGetClient(String url,Map<String, String> paramsMap, Map<String, String> headers, String token) {

        HttpClient httpclient = HttpClientBuilder.create().build();
        try {
            URI uri;
            if (paramsMap != null && !paramsMap.isEmpty()){
                List<NameValuePair> params = new ArrayList<>();

                for (String key: paramsMap.keySet()) {
                    params.add( new BasicNameValuePair( key, paramsMap.get(key) ) );
                }
                uri = new URI( url + "?" + URLEncodedUtils.format( params, "utf-8" ));
            }else {
                uri = new URI(url);
            }

            // 2：创建http的发送方式对象，是GET还是post
            HttpGet httpGet = new HttpGet(uri);

            // 3：创建要发送的实体，就是key-value的这种结构，借助于这个类，可以实现文件和参数同时上传，很简单的。
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    httpGet.setHeader(key, headers.get(key));
                }
            }
            if (token != null && !token.isEmpty()) {
                httpGet.setHeader("authorization", token);
            }
            // 4：执行httppost对象，从而获得信息
            HttpResponse response = httpclient.execute(httpGet);
            org.apache.http.HttpEntity resEntity = response.getEntity();

            // 获得返回来的信息，转化为字符串string
            return EntityUtils.toString(resEntity);
        } catch (IllegalStateException | IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            }
        }
        return "{}";
    }

    /**
     * put请求
     */
    public static String httpPutClient(String url,Map<String, String> params, Map<String, String> headers, String token) {
        HttpClient httpclient = HttpClientBuilder.create().build();
        try {
            // 2：创建http的发送方式对象，是GET还是post
            HttpPut httpPut = new HttpPut(url);

            // 3：创建要发送的实体，就是key-value的这种结构，借助于这个类，可以实现文件和参数同时上传，很简单的。
            JSONObject jsonObject = new JSONObject();
            if (params != null && !params.isEmpty()) {
                for (String key : params.keySet()) {
                    jsonObject.put(key, params.get(key));
                }
                StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");//解决中文乱码问题
                httpPut.setEntity(entity);
            }
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    httpPut.setHeader(key, headers.get(key));
                }
            }
            if (token != null && !token.isEmpty()) {
                httpPut.setHeader("authorization", token);
            }
            // 4：执行httppost对象，从而获得信息
            HttpResponse response = httpclient.execute(httpPut);
            org.apache.http.HttpEntity resEntity = response.getEntity();

            // 获得返回来的信息，转化为字符串string
            return EntityUtils.toString(resEntity);
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            }
        }
        return "{}";
    }

    /**
     * put请求
     */
    public static String httpDeleteClient(String url,Map<String, String> paramsMap, Map<String, String> headers, String token) {
        HttpClient httpclient = HttpClientBuilder.create().build();
        try {
            URI uri;
            if (paramsMap != null && !paramsMap.isEmpty()){
                List<NameValuePair> params = new ArrayList<>();

                for (String key: paramsMap.keySet()) {
                    params.add( new BasicNameValuePair( key, paramsMap.get(key) ) );
                }
                uri = new URI( url + "?" + URLEncodedUtils.format( params, "utf-8" ));
            }else {
                uri = new URI(url);
            }

            // 2：创建http的发送方式对象，是GET还是post
            HttpDelete httpDelete = new HttpDelete(uri);

            // 3：创建要发送的实体，就是key-value的这种结构，借助于这个类，可以实现文件和参数同时上传，很简单的。
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    httpDelete.setHeader(key, headers.get(key));
                }
            }
            if (token != null && !token.isEmpty()) {
                httpDelete.setHeader("authorization", token);
            }
            // 4：执行httppost对象，从而获得信息
            HttpResponse response = httpclient.execute(httpDelete);
            org.apache.http.HttpEntity resEntity = response.getEntity();

            // 获得返回来的信息，转化为字符串string
            return EntityUtils.toString(resEntity);
        } catch (IllegalStateException | IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            }
        }
        return "{}";
    }

}

```
