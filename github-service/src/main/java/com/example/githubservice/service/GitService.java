package com.example.githubservice.service;

import com.example.githubservice.controller.Config;
import com.example.githubservice.dto.Clone2RepoResponse;
import com.example.githubservice.helper.GitRepoHelper;
import com.example.githubservice.payload.request.CloneRepoRequest;
import com.example.githubservice.payload.response.BranchesResponse;
import com.example.githubservice.payload.response.CommitResponse;
import com.example.githubservice.utils.DeleteFileVisitor;
import com.example.githubservice.utils.DirectoryUtils;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.example.githubservice.helper.GitRepoHelper.visitCommits;

@Service
public class GitService {

    private static final Logger logger = LoggerFactory.getLogger(GitService.class);

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

    public BranchesResponse fetchGitBranches(String gitUrl, String username, String pat)
    {
        Collection<Ref> refs;
        BranchesResponse response = new BranchesResponse();
        try {
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
        } catch (InvalidRemoteException e) {
            logger.error(" InvalidRemoteException occurred in fetchGitBranches",e);
            e.printStackTrace();
        } catch (TransportException e) {
            logger.error(" TransportException occurred in fetchGitBranches",e);
        } catch (GitAPIException e) {
            logger.error(" GitAPIException occurred in fetchGitBranches",e);
        }
        return response;
    }

    public List<CommitResponse> getAllCommits(CloneRepoRequest request) {
        if(Config.pathMap.get(request.hashCode()) == null) {
            try {
                String path = cloneRepo(
                        request.getUrl(), request.getRepoName(),
                        request.getUserName(), request.getPat());
                Config.pathMap.put(request.hashCode(), path);
                List<CommitResponse> responses = visitCommits(path);
                return responses;
            } catch (GitAPIException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String path = Config.pathMap.get(request.hashCode());
            List<CommitResponse> responses = visitCommits(path);
            return responses;
        }
        return new ArrayList<>();
    }

}
