package com.example.githubservice.controller;

import com.example.githubservice.config.UserConfig;
import com.example.githubservice.dto.Response;
import com.example.githubservice.helper.ResponseHelper;
import com.example.githubservice.service.GithubService;
import com.example.githubservice.utils.Log.ClientLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

@RestController
public class GithubController {

    private Logger logger  = LogManager.getLogger(GithubController.class);

    @Autowired
    ResponseHelper responseHelper;

    @Autowired
    GithubService githubService;

    // Authenticate user and get access token
    @GetMapping("/api/auth")
    public ResponseEntity<?> githubAuth() {
        HttpHeaders headers = new HttpHeaders();
        String url = UriComponentsBuilder.fromHttpUrl("https://github.com/login/oauth/authorize")
                .queryParam("client_id", "aad946154dbec11155fb")
                .queryParam("scope", "repo, gist")
                .toUriString();
        headers.setLocation(URI.create(url));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @RequestMapping("/api/authenticated")
    public ResponseEntity<?> redirect(@RequestParam String code) {
        HttpHeaders headers = new HttpHeaders();
        String url = UriComponentsBuilder.fromHttpUrl("https://github.com/login/oauth/access_token")
                .queryParam("client_id", "aad946154dbec11155fb")
                .queryParam("client_secret", "59a5e02e7558eebdf9e93f200ff60a7425c67ec2")
                .queryParam("code", code)
                .toUriString();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setLocation(URI.create(url));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers),String.class);
        Response PAT = responseHelper.convertPATResponseToObject(response.getBody());
        UserConfig.PERSONAL_ACCESS_TOKEN = PAT.getAccess_token();
        UserConfig.SCOPE = PAT.getScope();
        UserConfig.USERNAME = responseHelper.getUserNameFromResponse(UserConfig.PERSONAL_ACCESS_TOKEN);

        logger.log(ClientLevel.CLIENT, "Authenticated!");

        return new ResponseEntity<Object>(PAT, HttpStatus.OK);
    }

    // Get user info
    @GetMapping("/api/user-info")
    public ResponseEntity<?> getUserInfo() {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/user";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", "application/vnd.github.v3+json");
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>("Need to authenticated with github first!", HttpStatus.UNAUTHORIZED);
    }

    //Get all repository
    @GetMapping("/api/user/repos")
    public ResponseEntity<?> getAllRepositories() {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/user/repos";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", "application/vnd.github.v3+json");
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>("Need to authenticated with github first!", HttpStatus.UNAUTHORIZED);
    }

    //Get repository info
    @GetMapping("/api/user/repo/{repoName}")
    public ResponseEntity<?> getRepositoryInfo(@PathVariable String repoName) {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/repos/" + UserConfig.USERNAME + "/" + repoName;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", "application/vnd.github.v3+json");
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>("Need to authenticated with github first!", HttpStatus.UNAUTHORIZED);
    }

    //Get all branches
    @GetMapping("/api/user/repo/{repoName}/branches")
    public ResponseEntity<?> getAllBranchesOfARepo(@PathVariable String repoName) {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/repos/" + UserConfig.USERNAME + "/" + repoName + "/branches";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", "application/vnd.github.v3+json");
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>("Need to authenticated with github first!", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/clone/{repoName}")
    public ResponseEntity<?> cloneRepo(@RequestParam String url, @PathVariable String repoName) throws GitAPIException, IOException {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            githubService.cloneRepo(url, repoName);
            return ResponseEntity.ok("Clone done");
        }
        return new ResponseEntity<>("Need to authenticated with github first!", HttpStatus.UNAUTHORIZED);
    }

}
