package com.example.githubservice.service;

import com.example.githubservice.config.UserConfig;
import com.example.githubservice.utils.Directory.DeleteFileVisitor;
import com.example.githubservice.utils.Directory.DirectoryUtils;
import com.example.githubservice.utils.Log.ClientLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
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

}
