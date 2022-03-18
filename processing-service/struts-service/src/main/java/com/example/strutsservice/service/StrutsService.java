package com.example.strutsservice.service;

import com.example.strutsservice.utils.communicator.Request;
import com.example.strutsservice.utils.communicator.Response;

public interface StrutsService {

    public Response getDependency(Request request);
}
