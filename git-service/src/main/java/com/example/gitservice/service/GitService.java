package com.example.gitservice.service;

import com.example.gitservice.dto.Clone2RepoResponse;
import com.example.gitservice.dto.BranchesResponse;
import com.example.gitservice.dto.CommitResponse;
import com.example.gitservice.utils.DeleteFileVisitor;
import com.example.gitservice.utils.DirectoryUtils;
import com.example.gitservice.utils.ZipUtils;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.gitservice.helper.GitRepoHelper.visitCommits;

@Service
public class GitService {

    @Autowired
    ZipUtils zipUtils;

    private static final Logger logger = LoggerFactory.getLogger(GitService.class);

    public String cloneRepo(String url, String repoName, String username, String pat) throws GitAPIException, IOException {
        logger.info("Cloning repository: {}", repoName);
        String pathToSaved = "./project/" + username + "/" + repoName;

        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());

        Git git = Git.cloneRepository()
                .setURI(url)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
                .setDirectory(new File(pathToSaved))
                .call();
        git.getRepository().close();
        logger.info("Done cloning repository: {}", repoName);
        zipUtils.pack(pathToSaved, pathToSaved + ".zip");
        return pathToSaved;
    }

    public String cloneRepoByBranchName(String url, String repoName, String branchName, String username, String pat) throws GitAPIException, IOException {
        logger.info("Cloning repository {} in branch {}", repoName, branchName);
        String pathToSaved = "./project/anonymous/" + repoName + "-" + branchName;

        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());

        Git git = Git.cloneRepository()
                .setURI(url)
                .setBranch(branchName)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
                .setDirectory(new File(pathToSaved))
                .call();
        git.getRepository().close();
        logger.info("Done cloning repository {} in branch {}", repoName, branchName);
        zipUtils.pack(pathToSaved, pathToSaved + ".zip");
        return pathToSaved;
    }

    public String cloneRepoByCommit(String url, String repoName, String commitSha, String username, String pat) throws GitAPIException, IOException {
        String pathToSaved = "./project/anonymous/" + repoName + "-" + commitSha;
        logger.info("Cloning repository {} with commit {}", repoName, commitSha);
        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());

        CloneCommand cloneCommand = Git.cloneRepository()
                .setURI(url)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
                .setDirectory(new File(pathToSaved));

        Git clonedRepo = cloneCommand.call();

        CheckoutCommand checkoutCommand = clonedRepo.checkout()
                .setName(commitSha);

        Ref ref = checkoutCommand.call();
        clonedRepo.getRepository().close();
        logger.info("Done cloning repository {} with commit {}", repoName, commitSha);
        zipUtils.pack(pathToSaved, pathToSaved + ".zip");
        return pathToSaved;

    }

    public Clone2RepoResponse clone2RepoByBranch
            (String url, String repo, String branch1, String branch2, String username, String pat)
            throws GitAPIException, IOException {
        logger.info("Cloning repo in 2 branches: {}, {}", branch1, branch2);
        String path1 = cloneRepoByBranchName(url, repo, branch1, username, pat);
        String path2 = cloneRepoByBranchName(url, repo, branch2, username, pat);
        logger.info("Done cloning repo in 2 branches: {}, {}", branch1, branch2);
        return new Clone2RepoResponse(path1, path2);
    }

    public Clone2RepoResponse clone2RepoByCommits
            (String url, String repo,
             String commitSha1, String commitSha2,
             String username, String pat) throws GitAPIException, IOException {
        logger.info("Cloning repo with 2 commits: {}, {}", commitSha1, commitSha2);
        String path1 = cloneRepoByCommit(url, repo, commitSha1, username, pat);
        String path2 = cloneRepoByCommit(url, repo, commitSha2, username, pat);
        logger.info("Done cloning repo with 2 commits: {}, {}", commitSha1, commitSha2);
        return new Clone2RepoResponse(path1, path2);
    }

    public BranchesResponse fetchGitBranches(String gitUrl, String username, String pat) throws GitAPIException {
        logger.info("Fetching repo branches...");
        Collection<Ref> refs;
        BranchesResponse response = new BranchesResponse();
        refs = Git.lsRemoteRepository()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
                .setHeads(true)
                .setRemote(gitUrl)
                .call();
        List<String> shortName = new ArrayList<>();
        List<String> fullName = new ArrayList<>();
        for (Ref ref : refs) {
            fullName.add(ref.getName());
            shortName.add(ref.getName().substring(ref.getName().lastIndexOf("/")+1, ref.getName().length()));
        }
        Collections.sort(shortName);
        Collections.sort(fullName);
        response.setFullName(fullName);
        response.setShortName(shortName);
        logger.info("Fetching branches done!");
        return response;
    }

    public List<CommitResponse> getAllCommitsInBranch(String url, String repoName, String branch, String user, String token) throws GitAPIException, IOException {
        String path = cloneRepoByBranchName(url, repoName, branch, user, token);
        List<CommitResponse> responses = visitCommits(path);
        return responses;
    }

    public List<CommitResponse> getAllCommits(String url, String repoName, List<String> branches, String user, String token) throws GitAPIException, IOException {
        Set<CommitResponse> commitSet = new HashSet<>();
        for(String branch : branches) {
            commitSet.addAll(getAllCommitsInBranch(url, repoName, branch, user, token));
        }
        return commitSet
                .stream()
                .sorted(Comparator.comparing(CommitResponse::getTime))
                .collect(Collectors.toList());
    }

    public boolean isGitRepo(String path) {
        try {
            Git git = Git.open(new File(path));
            Repository repo = git.getRepository();
            for (Ref ref : repo.getAllRefs().values()) {
                if (ref.getObjectId() == null)
                    continue;
                return true;
            }
            return false;
        } catch(Exception e) {
            return false;
        }
    }

}
