package com.example.githubservice.service;

import com.example.githubservice.config.UserConfig;
import com.example.githubservice.utils.Directory.DeleteFileVisitor;
import com.example.githubservice.utils.Directory.DirectoryUtils;
import com.example.githubservice.utils.Log.ClientLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@Service
public class GithubService {

    private Logger logger = LogManager.getLogger(GithubService.class);

    public void cloneRepo(String url, String repoName) throws GitAPIException, IOException {

        String pathToSaved = "./project/anonymous/" + repoName;

        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());

        logger.log(ClientLevel.CLIENT, "Cloning " + repoName + "...");
        Git git = Git.cloneRepository()
                .setURI(url)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(UserConfig.USERNAME, UserConfig.PERSONAL_ACCESS_TOKEN))
                .setDirectory(new File(pathToSaved))
                .call();
        git.getRepository().close();
        logger.log(ClientLevel.CLIENT, "Cloned!");
    }

    public void cloneRepoByBranchName(String url, String repoName, String branchName) throws GitAPIException, IOException {

        String pathToSaved = "./project/anonymous/" + repoName + "-" + branchName;

        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());

        logger.log(ClientLevel.CLIENT, "Cloning " + repoName + " in branch " + branchName + "...");
        Git git = Git.cloneRepository()
                .setURI(url)
                .setBranch(branchName)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(UserConfig.USERNAME, UserConfig.PERSONAL_ACCESS_TOKEN))
                .setDirectory(new File(pathToSaved))
                .call();
        git.getRepository().close();
        logger.log(ClientLevel.CLIENT, "Cloned!");
    }

    public void cloneRepoByCommit(String url, String repoName, String commitSha) throws GitAPIException, IOException {
        String pathToSaved = "./project/anonymous/" + repoName + "-" + commitSha;

        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());

        logger.log(ClientLevel.CLIENT, "Cloning " + repoName + " in branch " + " with commit: " + commitSha);

        CloneCommand cloneCommand = Git.cloneRepository()
                .setURI(url)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(UserConfig.USERNAME, UserConfig.PERSONAL_ACCESS_TOKEN))
                .setDirectory(new File(pathToSaved));

        Git clonedRepo = cloneCommand.call();

        CheckoutCommand checkoutCommand = clonedRepo.checkout()
                .setName(commitSha);

        Ref ref = checkoutCommand.call();

        clonedRepo.getRepository().close();
        logger.log(ClientLevel.CLIENT, "Cloned!");

    }

    public void clone2RepoByBranch
            (String url, String repo, String branch1, String branch2) throws GitAPIException, IOException {
        cloneRepoByBranchName(url, repo, branch1);
        cloneRepoByBranchName(url, repo, branch2);
    }

    public void clone2RepoByCommits
            (String url, String repo,
             String commitSha1, String commitSha2) throws GitAPIException, IOException {
        cloneRepoByCommit(url, repo, commitSha1);
        cloneRepoByCommit(url, repo, commitSha2);
    }

}
