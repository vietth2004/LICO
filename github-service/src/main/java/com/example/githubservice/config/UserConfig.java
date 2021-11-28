package com.example.githubservice.config;

import org.springframework.context.annotation.Scope;

@Scope("singleton")
public class UserConfig {

    public static String PERSONAL_ACCESS_TOKEN;
    public static String SCOPE;
    public static String USERNAME;

}
