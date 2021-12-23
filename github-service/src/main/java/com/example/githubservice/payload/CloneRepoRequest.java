package com.example.githubservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloneRepoRequest {

    private String url;
    private String repoName;
    private String userName;
    private String pat;

}
