package com.example.githubservice.controller;

import com.example.githubservice.payload.request.CloneRepoRequest;
import com.example.githubservice.payload.response.BranchesResponse;
import com.example.githubservice.payload.response.CommitResponse;
import com.example.githubservice.payload.response.RepoInfoResponse;
import com.example.githubservice.service.GitService;
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

    @Autowired
    private GitService gitService;

    @GetMapping("/repo/branches")
    public ResponseEntity<?> getRepoBranches(@RequestBody CloneRepoRequest request) {
        BranchesResponse branches = gitService.fetchGitBranches(request.getUrl(), request.getUserName(), request.getPat());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(branches);
    }

    @GetMapping("/repo/commits")
    public ResponseEntity<?> getRepoCommits(@RequestBody CloneRepoRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(gitService.getAllCommits(request));
    }

    @GetMapping("/repo/info")
    public ResponseEntity<?> getRepoInfo(@RequestBody CloneRepoRequest request) {
        BranchesResponse branches = gitService.fetchGitBranches(request.getUrl(), request.getUserName(), request.getPat());
        List<CommitResponse> commits = gitService.getAllCommits(request);
        RepoInfoResponse response = new RepoInfoResponse(branches, commits);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
