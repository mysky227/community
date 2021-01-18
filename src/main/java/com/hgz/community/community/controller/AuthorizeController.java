package com.hgz.community.community.controller;

import com.hgz.community.community.dto.AccessTokenDTO;
import com.hgz.community.community.provider.GithubProvider;
import com.hgz.community.community.dto.GithubUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.uri}")
    private String uri;

    @GetMapping("/callback")
    public String callback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state
    ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(uri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO user = githubProvider.getUser(accessToken);
        return "index";
    }
}
