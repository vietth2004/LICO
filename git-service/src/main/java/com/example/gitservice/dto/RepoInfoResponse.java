package com.example.gitservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepoInfoResponse {

    private BranchesResponse branches;
    private List<CommitResponse> commits;

}
