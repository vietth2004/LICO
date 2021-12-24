package com.example.githubservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloneRepoByCommitRequest {

    private String url;
    private String repoName;
    private String commitSha;
    private String username;
    private String pat;

}
