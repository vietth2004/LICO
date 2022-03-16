package com.example.gitservice.helper;

import com.example.gitservice.dto.CommitResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GitRepoHelper {

    private final static Logger logger = LoggerFactory.getLogger(GitRepoHelper.class);

    public static List<CommitResponse> visitCommits(String path) {
        logger.info("visiting commits...");
        List<CommitResponse> commitResponses = new ArrayList<>();

        try {

            Repository repo = new FileRepository(path + "/.git");
            Git git = new Git(repo);
            logger.info("walking repo: {}", path);
            RevWalk walk = new RevWalk(repo);

            List<Ref> branches = git.branchList().call();

            logger.info("List of branches: {}", branches);

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

}
