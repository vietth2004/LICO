package com.example.githubservice.payload.versioncompare;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Version {
    private String oldVersion;

    private String newVersion;

}
