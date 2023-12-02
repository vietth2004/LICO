package com.example.gitservice.service;

import com.example.gitservice.dto.BranchesResponse;
import com.example.gitservice.dto.Clone2RepoResponse;
import com.example.gitservice.dto.CommitResponse;
import com.example.gitservice.thread.GetCommitThread;
import com.example.gitservice.thread.ZipFolderThread;
import com.example.gitservice.utils.DeleteFileVisitor;
import com.example.gitservice.utils.DirectoryUtils;
import com.example.gitservice.utils.ZipUtils;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.example.gitservice.helper.GitRepoHelper.visitCommits;

@Service
public class GitService {

    @Autowired
    ZipUtils zipUtils;

    private static final Logger logger = LoggerFactory.getLogger(GitService.class);

    public String cloneRepo(String url, String repoName, String username, String pat) throws IOException {

        logger.info("Cloning repository: {}", repoName);
        String pathToSaved = "./project/" + username + "/" + repoName;

        if (Files.exists(Path.of(pathToSaved))) {
            if (isGitRepo(pathToSaved)) {
                return updateCloneCommand(pathToSaved, username, repoName, pat, "master");
            }
        }

        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());

        Git git = null;
        try {
            git = Git.cloneRepository()
                    .setURI(url)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
                    .setDirectory(new File(pathToSaved))
                    .call();
        } catch (GitAPIException e) {
            git.getRepository().close();
            try {
                git.gc().call();
            } catch (GitAPIException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        git.getRepository().close();
        try {
            git.gc().call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        logger.info("Done cloning repository: {}", repoName);
        Executors.newCachedThreadPool().execute(new ZipFolderThread(pathToSaved, pathToSaved + ".zip"));
        return pathToSaved;
    }

    public String cloneRepoByBranchName(String url, String repoName, String branchName, String username, String pat) throws IOException {
        logger.info("Cloning repository {} in branch {}", repoName, branchName);
        String pathToSaved = "./project/" + username + "/" + repoName + "-" + branchName;
        if (Files.exists(Path.of(pathToSaved))) {
            if (isGitRepo(pathToSaved)) {
                return updateCloneCommand(pathToSaved, username, repoName, pat, branchName);
            }
        }
        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());

        Git git = null;
        try {
            git = Git.cloneRepository()
                    .setURI(url)
                    .setBranch(branchName)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
                    .setDirectory(new File(pathToSaved))
                    .call();
        } catch (GitAPIException e) {
            git.getRepository().close();
            try {
                git.gc().call();
            } catch (GitAPIException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        git.getRepository().close();
        try {
            git.gc().call();
        } catch (GitAPIException ex) {
            ex.printStackTrace();
        }
        logger.info("Done cloning repository {} in branch {}", repoName, branchName);
        Executors.newCachedThreadPool().execute(new ZipFolderThread(pathToSaved, pathToSaved + ".zip"));
        return pathToSaved;
    }

    public String cloneRepoByCommit(String url, String repoName, String commitSha, String username, String pat) throws IOException {
        String pathToSaved = "./project/" + username + "/" + repoName + "-" + commitSha;
        System.out.println(pathToSaved);
        logger.info("Cloning repository {} with commit {}", repoName, commitSha);
        DirectoryUtils.deleteDir(new File(pathToSaved));
        Files.walkFileTree(Path.of(pathToSaved), new DeleteFileVisitor());
        if (Files.exists(Path.of(pathToSaved))) {
            if (isGitRepo(pathToSaved)) {
                return pathToSaved;
            }
        }
        System.out.println(url);
//        username = "ducduongn";
//        System.out.println(username + " " + pat);
//        CloneCommand cloneCommand = Git.cloneRepository()
//                .setURI(url)
//                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
//                .setDirectory(new File(pathToSaved));
        System.out.println("11111111111111111111111111111111");
        Git clonedRepo = null;
        try {
            clonedRepo = Git.cloneRepository()
                    .setURI(url)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
                    .setDirectory(new File(pathToSaved))
                    .call();
            System.out.println("66666666666666666666666666666666666");
        } catch (GitAPIException e) {
//            cloneCommand.getRepository().close();
            clonedRepo.getRepository().close();
            try {
                clonedRepo.gc().call();
            } catch (GitAPIException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        CheckoutCommand checkoutCommand = clonedRepo.checkout()
                .setName(commitSha);
        Ref ref;
        try {
            ref = checkoutCommand.call();
        } catch (GitAPIException e) {
            clonedRepo.getRepository().close();
            try {
                clonedRepo.gc().call();
            } catch (GitAPIException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        clonedRepo.getRepository().close();
        logger.info("Done cloning repository {} with commit {}", repoName, commitSha);
        Executors.newCachedThreadPool().execute(new ZipFolderThread(pathToSaved, pathToSaved + ".zip"));
        System.out.println("0000000000000000000000000000000000");
        return pathToSaved;

    }

    public String updateCloneCommand(String pathToSaved, String username, String repoName, String pat, String branch) throws IOException {
        File gitWorkDir = new File(pathToSaved);
        Git git = Git.open(gitWorkDir);
        PullResult result = null;
        try {
            result = git.pull()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, pat))
                    .setRemote("origin")
                    .setRemoteBranchName(branch)
                    .call();
        } catch (GitAPIException e) {
            git.getRepository().close();
            try {
                git.gc().call();
            } catch (GitAPIException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        git.getRepository().close();
        try {
            git.gc().call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        if (result.isSuccessful()) {
            logger.info("Done cloning repository: {}", repoName);
            return pathToSaved;
        } else {
            logger.info("Error in cloning repository: {}", repoName);
            return null;
        }
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
            shortName.add(ref.getName().substring(ref.getName().lastIndexOf("/") + 1, ref.getName().length()));
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
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<List<CommitResponse>>> futures = new ArrayList<>();
        for (String branch : branches) {
            Future<List<CommitResponse>> future = executor.submit(new GetCommitThread(url, repoName, branch, user, token));
            futures.add(future);
        }
        executor.shutdown();
        for (Future<List<CommitResponse>> future : futures) {
            try {
                commitSet.addAll(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
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
        } catch (Exception e) {
            return false;
        }
    }

}
