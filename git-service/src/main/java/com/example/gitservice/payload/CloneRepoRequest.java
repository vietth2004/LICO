package com.example.gitservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloneRepoRequest {

    private String url;
    private String repo;
    private String username;
    private String pat;

}
