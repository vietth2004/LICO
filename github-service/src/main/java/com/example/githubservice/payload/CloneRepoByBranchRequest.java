package com.example.githubservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloneRepoByBranchRequest {

    private String url;
    private String repo;
    private String branch;
    private String username;
    private String pat;

}
