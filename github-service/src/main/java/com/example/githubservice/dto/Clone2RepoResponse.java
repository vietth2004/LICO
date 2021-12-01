package com.example.githubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clone2RepoResponse {
    private String repo1Path;
    private String repo2Path;
}
