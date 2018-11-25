package com.example.loid.btlink;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class httpRequest {

    OkHttpClient okHttpClient = new OkHttpClient();
    Request request;
    private String response_body = "";
    private String response_header = "";

    public void get(String URL) {
        // 构造Request对象
        request = new Request.Builder().url(URL).build();
        // 构建Call对象
        Call call = okHttpClient.newCall(request);
        // 异步 callback
        try {
            Response response = call.execute();
            this.response_body = response.body().string();
            this.response_header = response.headers().toString();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public String text() {
        return this.response_body;
    }

    public String headers() {
        return this.response_header;
    }

    public static void main(String[] args) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        int i = 0;
//        httpRequest request = new httpRequest();
//        request.get("https://www.btcherries.com/search/DV-1200-1.html");
//
//        System.out.println(request.response_body);
//        Pattern pattern = Pattern.compile("<a href=\"/magnet/(.*?).html\" target=\"_blank\">(.*?)</a>");
//        Matcher matcher = pattern.matcher(request.response_body);
//
//        while(matcher.find()) {
//            System.out.println(matcher.group(1));
//            System.out.println(matcher.group(2));
//            i++;
//        }
        String s = "[\"";
        System.out.println(s.replaceAll("\\[", "\\\\[").replaceAll("\"", "'"));
    }
}
