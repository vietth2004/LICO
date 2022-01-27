package com.example.githubservice.controller;

import com.example.githubservice.dto.Clone2RepoResponse;
import com.example.githubservice.dto.CloneRepoPath;
import com.example.githubservice.dto.ErrorMessage;
import com.example.githubservice.payload.*;
import com.example.githubservice.payload.versioncompare.Response;
import com.example.githubservice.payload.versioncompare.Version;
import com.example.githubservice.service.GitService;
import com.example.githubservice.service.VersionCompare;
import mrmathami.cia.java.JavaCiaException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/api/git-service/")
public class CloneController {

    private final Logger logger = LoggerFactory.getLogger(CloneController.class);

    @Autowired
    GitService gitService;

    @Autowired
    VersionCompare versionCompare;

    /**
     * Clone repository
     * @param request
     * @return path where to save repo
     * @RequestParam(required = true) String url, @RequestParam(required = false, defaultValue = "") String pat
     */
    @PostMapping("/repo/clone")
    public ResponseEntity<?> cloneRepo(@RequestBody CloneRepoRequest request) {
        logger.info("/repo/clone");
        if(Config.pathMap.get(request.hashCode()) == null) {
            try {
                String path = gitService.cloneRepo(
                        request.getUrl(), request.getRepo(),
                        request.getUsername(), request.getPat());
                Config.pathMap.put(request.hashCode(), path);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new CloneRepoPath(path));
            } catch (GitAPIException e) {
                logger.error("Exception in CloneController: {}", e);
            } catch (IOException e) {
                logger.error("Exception in CloneController: {}", e);
            }
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessage("Exception while cloning repo: " + request.getUrl()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CloneRepoPath(Config.pathMap.get(request.hashCode())));
        }
    }

    /**
     * Clone repo by branch
     * @param request
     * @return path where to save repo
     */
    @PostMapping("/repo/clone/byBranch")
    public ResponseEntity<?> cloneRepoByBranch(@RequestBody CloneRepoByBranchRequest request) {

        logger.info("/repo/clone/byBranch");

        if(Config.pathMap.get(request.hashCode()) == null) {

            try {
                String path = gitService.cloneRepoByBranchName(
                        request.getUrl(), request.getRepo(),
                        request.getBranch(), request.getUsername(),
                        request.getPat()
                );
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new CloneRepoPath(path));
            } catch (GitAPIException e) {
                logger.error("Exception in CloneController: {}", e);
            } catch (IOException e) {
                logger.error("Exception in CloneController: {}", e);
            }
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessage("Exception while cloning repo: " + request.getUrl()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CloneRepoPath(Config.pathMap.get(request.hashCode())));
        }
    }

    /**
     * Clone repo by commit
     * @param request
     * @return path where to save repo
     */
    @PostMapping("/repo/clone/byCommit")
    public ResponseEntity<?> cloneRepoByCommit(@RequestBody CloneRepoByCommitRequest request) {

        logger.info("/repo/clone/byCommit");

        if(Config.pathMap.get(request.hashCode()) == null) {
            try {
                String path = gitService.cloneRepoByCommit(
                        request.getUrl(), request.getRepo(),
                        request.getCommitSha(), request.getUsername(),
                        request.getPat()
                );
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new CloneRepoPath(path));
            } catch (GitAPIException e) {
                logger.error("Exception in CloneController: {}", e);
            } catch (IOException e) {
                logger.error("Exception in CloneController: {}", e);
            }
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessage("Exception while cloning repo: " + request.getUrl()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CloneRepoPath(Config.pathMap.get(request.hashCode())));
        }
    }

    /**
     * Clone 2 repo by branch name
     * @param request
     * @return path where to save those repos
     */
    @PostMapping("/repos/clone/byBranch")
    public ResponseEntity<?> clone2RepoByBranch(@RequestBody Clone2RepoByBranchRequest request, @RequestParam boolean compare) {

        logger.info("/repos/clone/byBranch");

        try {
            Clone2RepoResponse response = gitService.clone2RepoByBranch(
                    request.getUrl(), request.getRepo(),
                    request.getBranch1(), request.getBranch2(),
                    request.getUsername(), request.getPat()
            );
            //verify compare parameter
            if(compare = false)
                return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
            else {
                Version version = new Version(response.getPath1(), response.getPath2());
                Response result = versionCompare.compare(version);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(result);
            }
        } catch (GitAPIException e) {
            logger.error("Exception in CloneController: {}", e);
        } catch (IOException e) {
            logger.error("Exception in CloneController: {}", e);
        } catch (JavaCiaException e) {
            logger.error("Exception in CloneController: {}", e);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Exception while cloning 2 repos by branch!"));
    }

    @PostMapping("/repos/clone/byCommit")
    public ResponseEntity<?> clone2RepoByCommit(@RequestBody Clone2RepoByCommitRequest request, @RequestParam boolean compare) {

        logger.info("/repos/clone/byCommit");

        try {
            Clone2RepoResponse response = gitService.clone2RepoByCommits(
                    request.getUrl(), request.getRepo(),
                    request.getCommit1(), request.getCommit2(),
                    request.getUsername(), request.getPat()
            );
            //verify compare parameter
            if(compare = false)
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(response);
            else {
                Version version = new Version(response.getPath1(), response.getPath2());
                Response result = versionCompare.compare(version);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(result);
            }
        } catch (GitAPIException e) {
            logger.error("Exception in CloneController: {}", e);
        } catch (IOException e) {
            logger.error("Exception in CloneController: {}", e);
        } catch (JavaCiaException e) {
            logger.error("Exception in CloneController: {}", e);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Exception while cloning 2 repos by commit!"));
    }

}
