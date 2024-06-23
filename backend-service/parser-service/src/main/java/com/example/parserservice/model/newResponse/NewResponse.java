package com.example.parserservice.model.newResponse;

import com.example.parserservice.model.Response;

import java.util.ArrayList;

public class NewResponse {
    private String address;
    private ArrayList nodes;

    private Integer rootId;

    private com.example.parserservice.model.Response oldResponse;

    public NewResponse(String address, ArrayList nodes, Response oldResponse, Integer rootId) {
        this.address = address;
        this.nodes = nodes;
        this.oldResponse = oldResponse;
        this.rootId = rootId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList nodes) {
        this.nodes = nodes;
    }

    public Response getOldResponse() {
        return oldResponse;
    }

    public void setOldResponse(Response oldResponse) {
        this.oldResponse = oldResponse;
    }
}
