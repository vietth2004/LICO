package com.example.versioncompareservice.model;

public class Version {
    private String older = new String();

    private String newer = new String();

    public Version() {
    }

    public String getOlder() {
        return older;
    }

    public void setOlder(String older) {
        this.older = older;
    }

    public String getNewer() {
        return newer;
    }

    public void setNewer(String newer) {
        this.newer = newer;
    }
}
