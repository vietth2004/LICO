package com.example.githubservice.helper;

public class GitUserHelper {

    public static boolean checkIfGitUserIsNull(String username, String pat) {
        if(username == null || pat == null) {
            return true;
        }
        return false;
    }

}
