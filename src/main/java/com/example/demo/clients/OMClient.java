package com.example.demo.clients;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class OMClient {
    public static String getOmStatus(String tag) {
        String omData = getOmApi(tag.replaceAll("#", ""));
        String status = null;
        if (omData.contains("\"state\":\"正常\"")) status = "正常O盟部落";
        if (omData.contains("\"state\":\"冻结\"")) status = "O盟冻结部落";
        if (omData.contains("\"state\":\"寄生虫\"")) status = "O盟黑名单";
        return status;
    }

    public static String getStatCol(String tag) {
        String omData = getOmApi(tag.replaceAll("#", ""));
        String color = null;
        if (omData.contains("\"state\":\"正常\"")) color = "badge bg-primary";
        if (omData.contains("\"state\":\"冻结\"")) color = "badge bg-info";
        if (omData.contains("\"state\":\"寄生虫\"")) color = "badge bg-danger";
        return color;
    }

    public static String getOmApi(String tag) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url;
        url = "https://omcoc.club/user/datas?tag=%23" + tag;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = null;
        try {
            assert response != null;
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
