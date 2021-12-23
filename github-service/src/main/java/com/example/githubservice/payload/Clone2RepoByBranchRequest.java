package com.example.githubservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clone2RepoByBranchRequest {

    private String url;
    private String repoName;
    private String branch1;
    private String branch2;
    private String username;
    private String pat;

}
