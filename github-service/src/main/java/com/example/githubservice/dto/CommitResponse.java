package com.example.githubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitResponse {

    private String name;
    private String author;
    private String branch;
    private Date time;
    private String message;

}
