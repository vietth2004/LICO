package com.example.gitservice.controller;

import com.example.gitservice.dto.BranchesResponse;
import com.example.gitservice.dto.CommitResponse;
import com.example.gitservice.dto.ErrorMessage;
import com.example.gitservice.dto.RepoInfoResponse;
import com.example.gitservice.service.GitService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/git-service/")
public class RepositoryController {

    private final Logger logger = LoggerFactory.getLogger(RepositoryController.class);

    @Autowired
    private GitService gitService;

    @GetMapping("/repo/branches")
    public ResponseEntity<?> getRepoBranches(
            @RequestParam String url,
            @RequestParam(required = false, defaultValue = "anonymous") String user,
            @RequestParam(required = false, defaultValue = "") String token
    ) {
        String repoName = "";
        BranchesResponse branches = null;
        try {
            repoName = Arrays
                    .stream(url.split("/"))
                    .filter(name -> name.endsWith(".git"))
                    .collect(Collectors.toList())
                    .get(0)
                    .replace(".git", "");
            branches = gitService.fetchGitBranches(url, user, token);
        } catch (GitAPIException e) {
            if (e instanceof TransportException)
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorMessage(
                                HttpStatus.UNAUTHORIZED.value(),
                                new Date(),
                                "Cannot fetch repo branches: " + url,
                                "Unauthorized/Invalid token to repo: " + repoName + ". Please check token in your request!"
                        ));
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            new Date(),
                            "Cannot fetch repo branches: " + url,
                            "Cannot open connection to repository. Please check repository url!"
                    ));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(branches);
    }

    @GetMapping("/repo/commits")
    public ResponseEntity<?> getRepoCommits(
            @RequestParam String url,
            @RequestParam String branch,
            @RequestParam(required = false, defaultValue = "anonymous") String user,
            @RequestParam(required = false, defaultValue = "") String token
    ) {
        logger.info("/repo/commits");
        String repoName = "";
        try {
            repoName = Arrays
                    .stream(url.split("/"))
                    .filter(name -> name.endsWith(".git"))
                    .collect(Collectors.toList())
                    .get(0)
                    .replace(".git", "");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(gitService.getAllCommitsInBranch(url, repoName, branch, user, token));
        } catch (GitAPIException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorMessage(
                            HttpStatus.UNAUTHORIZED.value(),
                            new Date(),
                            "Cannot fetch repo branches: " + url,
                            "Unauthorized/Invalid token to repo: " + repoName + ". Please check token in your request!"
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            new Date(),
                            "Cannot fetch repo branches: " + url,
                            "Cannot open connection to repository. Please check repository url!"
                    ));
        }
        return null;
    }

    @GetMapping("/repo/info")
    public ResponseEntity<?> getRepoInfo(
            @RequestParam String url,
            @RequestParam(required = false, defaultValue = "anonymous") String user,
            @RequestParam(required = false, defaultValue = "") String token
    ) {

        logger.info("/repo/info");
        String repoName = "";
        BranchesResponse branches = null;
        try {
            repoName = Arrays
                    .stream(url.split("/"))
                    .filter(name -> name.endsWith(".git"))
                    .collect(Collectors.toList())
                    .get(0)
                    .replace(".git", "");

            branches = gitService.fetchGitBranches(url, user, token);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        List<CommitResponse> commits = null;
        try {
            commits = gitService.getAllCommits(url, repoName, branches.getShortName(), user, token);
        } catch (GitAPIException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorMessage(
                            HttpStatus.UNAUTHORIZED.value(),
                            new Date(),
                            "Cannot fetch repo branches: " + url,
                            "Unauthorized/Invalid token to repo: " + repoName + ". Please check token in your request!"
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            new Date(),
                            "Cannot fetch repo branches: " + url,
                            "Cannot open connection to repository. Please check repository url!"
                    ));
        }
        RepoInfoResponse response = new RepoInfoResponse(branches, commits);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/repo/isGit")
    public ResponseEntity checkIfGitRepo(@RequestParam String path) {
        return ResponseEntity.ok(gitService.isGitRepo(path));
    }

}
