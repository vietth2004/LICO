package com.example.parserservice.service.state.model;

public class Parser {
    private boolean isEndProgress = false;
    private boolean isJavaServer = false;
    private boolean isXmlServer = false;
    private boolean isJspServer = false;
    private boolean isPropServer = false;

    public Parser() {
    }

    public Parser(boolean isEndProgress, boolean isJavaServer, boolean isXmlServer, boolean isJspServer, boolean isPropServer) {
        this.isEndProgress = isEndProgress;
        this.isJavaServer = isJavaServer;
        this.isXmlServer = isXmlServer;
        this.isJspServer = isJspServer;
        this.isPropServer = isPropServer;
    }

    public boolean isEndProgress() {
        return isEndProgress;
    }

    public boolean isJavaServer() {
        return isJavaServer;
    }

    public boolean isXmlServer() {
        return isXmlServer;
    }

    public boolean isJspServer() {
        return isJspServer;
    }

    public boolean isPropServer() {
        return isPropServer;
    }
}
