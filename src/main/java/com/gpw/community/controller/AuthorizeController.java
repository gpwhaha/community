package com.gpw.community.controller;

import com.gpw.community.Provider.GithupProvider;
import com.gpw.community.dto.AccessTokenDTO;
import com.gpw.community.dto.GithupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithupProvider githupProvider;

    @Value("${githup.client.id}")
    private String clientId;

    @Value("${githup.client.secret}")
    private String clientSecret;

    @Value("${githup.redirect.uri}")
    private String clientUri;

    @GetMapping("/calback")
    public String calback(@RequestParam(name = "code") String code,
                          @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githupProvider.getAccessToken(accessTokenDTO);
        GithupUser user = githupProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
