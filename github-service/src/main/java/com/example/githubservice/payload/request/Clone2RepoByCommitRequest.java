package com.example.githubservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clone2RepoByCommitRequest {

    private String url;
    private String repoName;
    private String commit1;
    private String commit2;
    private String username;
    private String pat;

}
