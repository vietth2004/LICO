package com.example.githubservice.controller;

import com.example.githubservice.config.ApplicationConfig;
import com.example.githubservice.config.GithubAPI;
import com.example.githubservice.config.UserConfig;
import com.example.githubservice.dto.Clone2RepoResponse;
import com.example.githubservice.dto.CloneRepoResponse;
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
    private static final String ERR_MSG = "Need to authenticated with github first!";

    @Autowired
    ResponseHelper responseHelper;

    @Autowired
    GithubService githubService;

    // Authenticate user and get access token
    @GetMapping("/api/auth")
    public ResponseEntity<?> githubAuth() {
        HttpHeaders headers = new HttpHeaders();
        String url = UriComponentsBuilder.fromHttpUrl("https://github.com/login/oauth/authorize")
                .queryParam("client_id", ApplicationConfig.CLIENT_ID)
                .queryParam("scope", "repo, gist")
                .toUriString();
        headers.setLocation(URI.create(url));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    // Capture redirect request then get access token
    @RequestMapping("/api/authenticated")
    public ResponseEntity<?> redirect(@RequestParam String code) {
        HttpHeaders headers = new HttpHeaders();
        String url = UriComponentsBuilder.fromHttpUrl("https://github.com/login/oauth/access_token")
                .queryParam("client_id", ApplicationConfig.CLIENT_ID)
                .queryParam("client_secret", ApplicationConfig.CLIENT_SECRET)
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
            headers.set("Accept", GithubAPI.ACCEPT_HEADER);
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //Get all repository
    @GetMapping("/api/user/repos")
    public ResponseEntity<?> getAllRepositories() {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/user/repos";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", GithubAPI.ACCEPT_HEADER);
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //Get repository info
    @GetMapping("/api/user/repo")
    public ResponseEntity<?> getRepositoryInfo(@RequestParam String repo) {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/repos/" + UserConfig.USERNAME + "/" + repo;
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
    @GetMapping("/api/user/repo/branches")
    public ResponseEntity<?> getAllBranchesOfARepo(@RequestParam String repo) {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/repos/" + UserConfig.USERNAME + "/" + repo + "/branches";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", GithubAPI.ACCEPT_HEADER);
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //Clone repo
    @GetMapping("/api/clone/")
    public ResponseEntity<?> cloneRepo(@RequestParam String url, @RequestParam String repo) throws GitAPIException, IOException {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String path = githubService.cloneRepo(url, repo);
            return new ResponseEntity<>(new CloneRepoResponse(path), HttpStatus.OK);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //Clone repo by branch name
    @GetMapping("/api/clone/branch/")
    public ResponseEntity<?> cloneRepoByBranchName
                (@RequestParam String url,
                 @RequestParam String repo,
                 @RequestParam String branch)
            throws GitAPIException, IOException {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String path = githubService.cloneRepoByBranchName(url, repo, branch);
            return new ResponseEntity<>(new CloneRepoResponse(path), HttpStatus.OK);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //Clone repo by commit sha
    @GetMapping("/api/clone/commit/")
    public ResponseEntity<?> cloneRepoByCommitSha
                (@RequestParam String url, @RequestParam String repo, @RequestParam String commit)
            throws GitAPIException, IOException {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String path = githubService.cloneRepoByCommit(url, repo, commit);
            return new ResponseEntity<>(new CloneRepoResponse(path), HttpStatus.OK);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //clone 2 repo by commits
    @GetMapping("/api/clone/byCommits")
    public ResponseEntity<?> clone2RepoByCommitSha
                (@RequestParam String url, @RequestParam String repo,
                 @RequestParam String commit1, @RequestParam String commit2) throws GitAPIException, IOException {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            Clone2RepoResponse response = githubService.clone2RepoByCommits(url, repo, commit1, commit2);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //clone 2 repo by branch
    @GetMapping("/api/clone/byBranches")
    public ResponseEntity<?> clone2RepoByBranchName
                (@RequestParam String url, @RequestParam String repo,
                 @RequestParam String branch1, @RequestParam String branch2) throws GitAPIException, IOException {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            Clone2RepoResponse response = githubService.clone2RepoByBranch(url, repo, branch1, branch2);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

    //Get all commit in repo
    @GetMapping("/api/user/repo/commits")
    public ResponseEntity<?> getAllCommitsOfARepo(@RequestParam String repo) {
        if(UserConfig.PERSONAL_ACCESS_TOKEN != null) {
            String url = "https://api.github.com/repos/" + UserConfig.USERNAME + "/" + repo + "/commits";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + UserConfig.PERSONAL_ACCESS_TOKEN);
            headers.set("Accept", GithubAPI.ACCEPT_HEADER);
            HttpEntity entity = new HttpEntity(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        return new ResponseEntity<>(ERR_MSG, HttpStatus.UNAUTHORIZED);
    }

}
