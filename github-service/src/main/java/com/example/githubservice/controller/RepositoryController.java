package com.example.githubservice.controller;

import com.example.githubservice.dto.Clone2RepoResponse;
import com.example.githubservice.dto.CloneRepoPath;
import com.example.githubservice.dto.ErrorMessage;
import com.example.githubservice.payload.Clone2RepoByBranchRequest;
import com.example.githubservice.payload.CloneRepoByBranchRequest;
import com.example.githubservice.payload.CloneRepoByCommitRequest;
import com.example.githubservice.payload.CloneRepoRequest;
import com.example.githubservice.service.GitService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/api/git")
public class RepositoryController {

    @Autowired
    GitService gitService;

    /**
     * Clone repository
     * @param request
     * @return path where to save repo
     */
    @GetMapping("/repo/clone")
    public ResponseEntity<?> cloneRepo(@RequestBody CloneRepoRequest request) {
        try {
            String path = gitService.cloneRepo(request.getUrl(), request.getRepoName(), request.getUserName(), request.getPat());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CloneRepoPath(path));
        } catch (GitAPIException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Exception while cloning repo: " + request.getUrl()));
    }

    /**
     * Clone repo by branch
     * @param request
     * @return path where to save repo
     */
    @GetMapping("/repo/clone/byBranch")
    public ResponseEntity<?> cloneRepoByBranch(@RequestBody CloneRepoByBranchRequest request) {
        try {
            String path = gitService.cloneRepoByBranchName(
                    request.getUrl(), request.getRepoName(),
                    request.getBranch(), request.getUsername(), request.getPat()
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CloneRepoPath(path));
        } catch (GitAPIException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Exception while cloning repo: " + request.getUrl()));
    }

    /**
     * Clone repo by commit
     * @param request
     * @return path where to save repo
     */
    @GetMapping("/repo/clone/byCommit")
    public ResponseEntity<?> cloneRepoByCommit(@RequestBody CloneRepoByCommitRequest request) {
        try {
            String path = gitService.cloneRepoByCommit(
                    request.getUrl(), request.getRepoName(),
                    request.getCommitSha(), request.getUsername(), request.getPat()
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CloneRepoPath(path));
        } catch (GitAPIException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Exception while cloning repo: " + request.getUrl()));
    }

    /**
     * Clone 2 repo by branch name
     * @param request
     * @return path where to save those repos
     */
    @GetMapping("/repos/clone/byBranch")
    public ResponseEntity<?> clone2RepoByBranch(@RequestBody Clone2RepoByBranchRequest request) {
        try {
            Clone2RepoResponse response = gitService.clone2RepoByBranch(
                    request.getUrl(), request.getRepoName(),
                    request.getBranch1(), request.getBranch2(),
                    request.getUsername(), request.getPat()
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (GitAPIException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Exception while cloning 2 repos by branch name!"));
    }

}
