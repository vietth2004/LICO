package com.example.gitservice.controller;

import com.example.gitservice.constant.HostIPConstants;
import com.example.gitservice.dto.Clone2RepoResponse;
import com.example.gitservice.dto.CloneRepoPath;
import com.example.gitservice.dto.ErrorMessage;
import com.example.gitservice.dto.parser.Request;
import com.example.gitservice.payload.versioncompare.Response;
import com.example.gitservice.payload.versioncompare.Version;
import com.example.gitservice.service.GitService;
import com.example.gitservice.service.VersionCompare;
import com.example.gitservice.utils.Utils;
import mrmathami.cia.java.JavaCiaException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/git-service/")
public class CloneController {

    private final Logger logger = LoggerFactory.getLogger(CloneController.class);

    @Autowired
    GitService gitService;

    @Autowired
    VersionCompare versionCompare;

    @Autowired
    private HostIPConstants ipConstants;

    /**
     * Clone repository
     *
     * @return path where to save repo
     * @RequestParam(required = true) String url, @RequestParam(required = false, defaultValue = "") String pat
     */
    @GetMapping("/repo/clone")
    public ResponseEntity<?> cloneRepo(
            @RequestParam String url,
            @RequestParam(required = false, defaultValue = "") String token,
            @RequestParam(required = false, defaultValue = "anonymous") String user,
            @RequestParam(required = false) boolean analysis,
            @RequestParam(name = "parser", required = false, defaultValue = "java-parser, spring-parser") List<String> parserList) {
        String repoName = Arrays
                .stream(url.split("/"))
                .filter(name -> name.endsWith(Constants.DOT_GIT))
                .collect(Collectors.toList())
                .get(0)
                .replace(Constants.DOT_GIT, "");
        System.out.println(token);
        try {
            String path = gitService.cloneRepo(url, repoName, user, token);
            if (analysis == false)
                return ResponseEntity.ok(new CloneRepoPath(path));
            else {
                RestTemplate restTemplate = new RestTemplate();
                String serverUrl = "http://" + ipConstants.getJavaServiceIp() + ":7002/api/java-service/pathParse"; //java-service
                ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, new CloneRepoPath(path), Request.class);
                com.example.gitservice.dto.parser.Response res = Utils.getResponse(parserList, request.getBody(), path);
                return ResponseEntity.ok(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(
                        HttpStatus.FORBIDDEN.value(),
                        new Date(),
                        "Cannot clone repo: " + url,
                        "Unauthorized/Invalid token to repo: " + repoName
                ));
    }

    /**
     * Clone repo by branch
     *
     * @return path where to save repo
     */
    @GetMapping("/repo/clone/byBranch")
    public ResponseEntity<?> cloneRepoByBranch(
            @RequestParam String url,
            @RequestParam(required = false, defaultValue = "master") String branch,
            @RequestParam(required = false, defaultValue = "") String token,
            @RequestParam(required = false, defaultValue = "anonymous") String user,
            @RequestParam(required = false) boolean analysis,
            @RequestParam(name = "parser", required = false, defaultValue = "java-parser, spring-parser") List<String> parserList) {
        String repoName = Arrays
                .stream(url.split("/"))
                .filter(name -> name.endsWith(Constants.DOT_GIT))
                .collect(Collectors.toList())
                .get(0)
                .replace(Constants.DOT_GIT, "");
        System.out.println(token);
        try {
            String path = gitService.cloneRepoByBranchName(url, repoName, branch, user, token);
            if (analysis == false)
                return ResponseEntity.ok(new CloneRepoPath(path));
            else {
                RestTemplate restTemplate = new RestTemplate();
                String serverUrl = "http://" + ipConstants.getJavaServiceIp() + ":7002/api/java-service/pathParse"; //java-service
                ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, new CloneRepoPath(path), Request.class);
                com.example.gitservice.dto.parser.Response res = Utils.getResponse(parserList, request.getBody(), path);
                return ResponseEntity.ok(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(
                        HttpStatus.FORBIDDEN.value(),
                        new Date(),
                        "Cannot clone repo: " + url + " in branch: " + branch,
                        "Unauthorized/Invalid token to repo: " + repoName
                ));
    }

    /**
     * Clone repo by commit
     *
     * @return path where to save repo
     */
    @GetMapping("/repo/clone/byCommit")
    public ResponseEntity<?> cloneRepoByCommit(
            @RequestParam String url,
            @RequestParam String commit,
            @RequestParam(required = false, defaultValue = "") String token,
            @RequestParam(required = false, defaultValue = "anonymous") String user,
            @RequestParam(required = false) boolean analysis,
            @RequestParam(name = "parser", required = false, defaultValue = "java-parser, spring-parser") List<String> parserList) {
        String repoName = Arrays
                .stream(url.split("/"))
                .filter(name -> name.endsWith(Constants.DOT_GIT))
                .collect(Collectors.toList())
                .get(0)
                .replace(Constants.DOT_GIT, "");
        System.out.println(commit);
        System.out.println(token);
        try {
            String path = gitService.cloneRepoByCommit(url, repoName, commit, user, token);
            if (analysis == false)
                return ResponseEntity.ok(new CloneRepoPath(path));
            else {
                RestTemplate restTemplate = new RestTemplate();
                String serverUrl = "http://" + ipConstants.getJavaServiceIp() + ":7002/api/java-service/pathParse"; //java-service
                ResponseEntity<Request> request = restTemplate.postForEntity(serverUrl, new CloneRepoPath(path), Request.class);
                com.example.gitservice.dto.parser.Response res = Utils.getResponse(parserList, request.getBody(), path);
                return ResponseEntity.ok(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(
                        HttpStatus.FORBIDDEN.value(),
                        new Date(),
                        "Cannot clone repo: " + url + " with commit: " + commit,
                        "Unauthorized/Invalid token to repo: " + repoName
                ));
    }

    /**
     * Clone 2 repo by branch name
     *
     * @return path where to save those repos
     */
    @GetMapping("/repos/clone/byBranch")
    public ResponseEntity<?> clone2RepoByBranch(
            @RequestParam String url,
            @RequestParam String branch1,
            @RequestParam String branch2,
            @RequestParam(required = false, defaultValue = "") String token,
            @RequestParam(required = false, defaultValue = "anonymous") String user,
            @RequestParam(required = false) boolean compare
    ) {
        String repoName = Arrays
                .stream(url.split("/"))
                .filter(name -> name.endsWith(Constants.DOT_GIT))
                .collect(Collectors.toList())
                .get(0)
                .replace(Constants.DOT_GIT, "");
        System.out.println(token);
        try {
            Clone2RepoResponse res = gitService.clone2RepoByBranch(url, repoName, branch1, branch2, user, token);
            if (compare == false)
                return ResponseEntity.ok(res);
            else {
                Version version = new Version(res.getPath1(), res.getPath2());
                Response comparedResult = versionCompare.compare(version);
                return ResponseEntity.ok(comparedResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorMessage(
                            HttpStatus.UNAUTHORIZED.value(),
                            new Date(),
                            "Cannot clone repo: " + url,
                            "Unauthorized/Invalid token to repo: " + repoName + ". Please check token in your request!"
                    ));
        } catch (JavaCiaException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessage(
                            HttpStatus.BAD_REQUEST.value(),
                            new Date(),
                            "Cannot compare 2 versions",
                            "Exception while comparing repo: " + repoName + " in 2 branches in request!"
                    ));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(
                        HttpStatus.FORBIDDEN.value(),
                        new Date(),
                        "Cannot clone repo: " + url,
                        "Unauthorized/Invalid token to repo: " + repoName
                ));
    }

    @GetMapping("/repos/clone/byCommit")
    public ResponseEntity<?> clone2RepoByCommit(
            @RequestParam String url,
            @RequestParam String commit1,
            @RequestParam String commit2,
            @RequestParam(required = false, defaultValue = "") String token,
            @RequestParam(required = false, defaultValue = "anonymous") String user,
            @RequestParam(required = false) boolean compare
    ) {
        System.out.println(token);
        String repoName = Arrays
                .stream(url.split("/"))
                .filter(name -> name.endsWith(Constants.DOT_GIT))
                .collect(Collectors.toList())
                .get(0)
                .replace(Constants.DOT_GIT, "");
        try {
            Clone2RepoResponse res = gitService.clone2RepoByCommits(url, repoName, commit1, commit2, user, token);
            if (compare == false)
                return ResponseEntity.ok(res);
            else {
                Version version = new Version(res.getPath1(), res.getPath2());
                Response comparedResult = versionCompare.compare(version);
                return ResponseEntity.ok(comparedResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorMessage(
                            HttpStatus.UNAUTHORIZED.value(),
                            new Date(),
                            "Cannot clone repo: " + url,
                            "Unauthorized/Invalid token to repo: " + repoName + ". Please check token in your request!"
                    ));
        } catch (JavaCiaException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessage(
                            HttpStatus.BAD_REQUEST.value(),
                            new Date(),
                            "Cannot compare 2 versions",
                            "Exception while comparing repo: " + repoName + " in 2 commits in request!"
                    ));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(
                        HttpStatus.FORBIDDEN.value(),
                        new Date(),
                        "Cannot clone repo: " + url,
                        "Unauthorized/Invalid token to repo: " + repoName
                ));
    }

}
