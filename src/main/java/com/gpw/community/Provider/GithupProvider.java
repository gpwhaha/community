package com.gpw.community.Provider;


import com.alibaba.fastjson.JSON;
import com.gpw.community.dto.AccessTokenDTO;
import com.gpw.community.dto.GithupUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithupProvider {
    public String getAccessToken (AccessTokenDTO accessTokenDTO){
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
         OkHttpClient client = new OkHttpClient();


            RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String token = string.split("&")[0].split("=")[1];
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    public GithupUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token"+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithupUser githupUser = JSON.parseObject(string, GithupUser.class);
            return githupUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
