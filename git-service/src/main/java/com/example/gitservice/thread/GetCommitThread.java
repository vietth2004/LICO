package com.example.gitservice.thread;

import com.example.gitservice.dto.CommitResponse;
import com.example.gitservice.utils.DeleteFileVisitor;
import com.example.gitservice.utils.DirectoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class GetCommitThread implements Callable {

    private String url;
    private String repoName;
    private String branch;
    private String user;
    private String token;

    public GetCommitThread(String url, String repoName, String branch, String user, String token) {
        this.url = url;
        this.repoName = repoName;
        this.branch = branch;
        this.user = user;
        this.token = token;
    }

    @Override
    public List<CommitResponse> call() throws Exception {
        String path = cloneRepoByBranchName(this.url, this.repoName, this.branch, this.user, this.token);
        List<CommitResponse> responses = visitCommits(path);
        return responses;
    }

    public String cloneRepoByBranchName(String url, String repoName, String branchName, String username, String pat) throws GitAPIException, IOException {
        log.info("Cloning repository {} in branch {}", repoName, branchName);
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
        log.info("Done cloning repository {} in branch {}", repoName, branchName);
        pack(pathToSaved, pathToSaved + ".zip");
        return pathToSaved;
    }

    public static List<CommitResponse> visitCommits(String path) {
        log.info("visiting commits...");
        List<CommitResponse> commitResponses = new ArrayList<>();

        try {

            Repository repo = new FileRepository(path + "/.git");
            Git git = new Git(repo);
            log.info("walking repo: {}", path);
            RevWalk walk = new RevWalk(repo);

            List<Ref> branches = git.branchList().call();

            log.info("List of branches: {}", branches);

            for (Ref branch : branches) {
                String branchName = branch.getName();

                Iterable<RevCommit> commits = git.log().all().call();

                for (RevCommit commit : commits) {
                    boolean foundInThisBranch = false;

                    RevCommit targetCommit = walk.parseCommit(repo.resolve(
                            commit.getName()));
                    for (Map.Entry<String, Ref> e : repo.getAllRefs().entrySet()) {
                        if (e.getKey().startsWith(Constants.R_HEADS)) {
                            if (walk.isMergedInto(targetCommit, walk.parseCommit(
                                    e.getValue().getObjectId()))) {
                                String foundInBranch = e.getValue().getName();
                                if (branchName.equals(foundInBranch)) {
                                    foundInThisBranch = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (foundInThisBranch) {
                        CommitResponse response = new CommitResponse();
                        response.setName(commit.getName());
                        response.setAuthor(commit.getAuthorIdent().getName());
                        response.setBranch(branch.getName());
                        response.setTime(new Date(commit.getCommitTime() * 1000L));
                        response.setMessage(commit.getFullMessage());
                        commitResponses.add(response);
                    }
                }
            }
            repo.close();
        } catch (NoHeadException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        } catch (AmbiguousObjectException e) {
            e.printStackTrace();
        } catch (IncorrectObjectTypeException e) {
            e.printStackTrace();
        } catch (MissingObjectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commitResponses;

    }

    public void pack(String sourceDirPath, String zipFilePath) throws IOException {
        log.info("Zipping folder {} into {}", sourceDirPath, zipFilePath);
        DirectoryUtils.deleteDir(new File(zipFilePath));
        Path p = Files.createFile(Paths.get(zipFilePath));
        Path pp = Paths.get(sourceDirPath);
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p));
             Stream<Path> paths = Files.walk(pp)) {
            paths
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
        log.info("Done zipping folder {}", sourceDirPath);
    }

}
