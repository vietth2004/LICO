package com.example.jsfservice.compare;

import com.example.jsfservice.dom.Node;
import com.example.jsfservice.dto.compare.CompareResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CompareService {
    CompareResponse getCompare(List<Node> oldVer, List<Node> newVer) throws ExecutionException, InterruptedException;
}
