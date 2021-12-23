package com.example.githubservice.service;

import com.example.githubservice.dto.Clone2RepoResponse;
import com.example.githubservice.utils.DeleteFileVisitor;
import com.example.githubservice.utils.DirectoryUtils;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class GitService {

    public String cloneRepo(String url, String repoName, String username, String pat) throws GitAPIException, IOException {

        String pathToSaved = "./project/anonymous/" + repoName;

        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());

        Git git = Git.cloneRepository()
                .setURI(url)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
                .setDirectory(new File(pathToSaved))
                .call();
        git.getRepository().close();

        return pathToSaved;
    }

    public String cloneRepoByBranchName(String url, String repoName, String branchName, String username, String pat) throws GitAPIException, IOException {

        String pathToSaved = "./project/anonymous/" + repoName + "-" + branchName;

        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());

        Git git = Git.cloneRepository()
                .setURI(url)
                .setBranchesToClone(Arrays.asList("refs/heads/" + branchName))
                .setNoCheckout(true)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
                .setDirectory(new File(pathToSaved))
                .call();
        git.getRepository().close();

        return pathToSaved;
    }

    public String cloneRepoByCommit(String url, String repoName, String commitSha, String username, String pat) throws GitAPIException, IOException {
        String pathToSaved = "./project/anonymous/" + repoName + "-" + commitSha;

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

        return pathToSaved;

    }

    public Clone2RepoResponse clone2RepoByBranch
            (String url, String repo, String branch1, String branch2, String username, String pat) throws GitAPIException, IOException {
        String path1 = cloneRepoByBranchName(url, repo, branch1, username, pat);
        String path2 = cloneRepoByBranchName(url, repo, branch2, username, pat);
        return new Clone2RepoResponse(path1, path2);
    }

    public Clone2RepoResponse clone2RepoByCommits
            (String url, String repo,
             String commitSha1, String commitSha2,
            String username, String pat) throws GitAPIException, IOException {
        String path1 = cloneRepoByCommit(url, repo, commitSha1, username, pat);
        String path2 = cloneRepoByCommit(url, repo, commitSha2, username, pat);
        return new Clone2RepoResponse(path1, path2);
    }

}
