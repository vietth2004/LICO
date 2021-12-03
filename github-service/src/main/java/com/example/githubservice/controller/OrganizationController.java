package com.example.githubservice.controller;

import com.example.githubservice.config.GithubAPI;
import com.example.githubservice.config.UserConfig;
import com.example.githubservice.helper.ResponseHelper;
import com.example.githubservice.service.GithubService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class OrganizationController {

    private Logger logger  = LogManager.getLogger(OrganizationController.class);
    private static final String ERR_MSG = "Need to authenticated with github first!";

    @Autowired
    ResponseHelper responseHelper;

    @Autowired
    GithubService githubService;

    //Get all repositories in user's organization
    @GetMapping("/api/org/repos")
    public ResponseEntity<?> getAllReposInOrg(@RequestParam String org) {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/orgs/" + org + "/repos";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", GithubAPI.ACCEPT_HEADER);
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    // Get org info
    @GetMapping("/api/org-info")
    public ResponseEntity<?> getOrgInfo(@RequestParam String org) {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/orgs/" + org;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", GithubAPI.ACCEPT_HEADER);
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //Get repository info in org
    @GetMapping("/api/org/repo")
    public ResponseEntity<?> getRepositoryInfoInOrg(@RequestParam String org, @RequestParam String repo) {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/repos/" + org + "/" + repo;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", GithubAPI.ACCEPT_HEADER);
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //Get all branches
    @GetMapping("/api/org/repo/branches")
    public ResponseEntity<?> getAllBranchesOfARepoInOrg(@RequestParam String org, @RequestParam String repo) {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/repos/" + org + "/" + repo + "/branches";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", GithubAPI.ACCEPT_HEADER);
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //Get all commits in repo
    @GetMapping("/api/org/repo/commits")
    public ResponseEntity<?> getAllCommitsOfARepoInOrg(@RequestParam String org, @RequestParam String repo, @RequestParam int page) {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/repos/" + org + "/" + repo + "/commits";
            String url_req = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("per_page", 100)
                    .queryParam("page", page)
                    .toUriString();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", GithubAPI.ACCEPT_HEADER);
            headers.setLocation(URI.create(url_req));
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url_req, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

}
