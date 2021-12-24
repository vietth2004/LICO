package com.example.githubservice.controller;

import com.example.githubservice.payload.CloneRepoRequest;
import com.example.githubservice.dto.BranchesResponse;
import com.example.githubservice.dto.CommitResponse;
import com.example.githubservice.dto.RepoInfoResponse;
import com.example.githubservice.service.GitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/git")
public class RepositoryController {

    private final Logger logger = LoggerFactory.getLogger(RepositoryController.class);

    @Autowired
    private GitService gitService;

    @GetMapping("/repo/branches")
    public ResponseEntity<?> getRepoBranches(@RequestBody CloneRepoRequest request) {

        logger.info("/repo/branches");

        BranchesResponse branches = gitService.fetchGitBranches(request.getUrl(), request.getUsername(), request.getPat());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(branches);
    }

    @GetMapping("/repo/commits")
    public ResponseEntity<?> getRepoCommits(@RequestBody CloneRepoRequest request) {

        logger.info("/repo/commits");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(gitService.getAllCommits(request));
    }

    @GetMapping("/repo/info")
    public ResponseEntity<?> getRepoInfo(@RequestBody CloneRepoRequest request) {

        logger.info("/repo/info");

        BranchesResponse branches = gitService.fetchGitBranches(request.getUrl(), request.getUsername(), request.getPat());
        List<CommitResponse> commits = gitService.getAllCommits(request);
        RepoInfoResponse response = new RepoInfoResponse(branches, commits);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
