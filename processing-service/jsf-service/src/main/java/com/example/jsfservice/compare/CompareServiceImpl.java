package com.example.jsfservice.compare;

import com.example.jsfservice.compare.thread.AddedNodeAnalyzer;
import com.example.jsfservice.compare.thread.ChangedNodeAnalyzer;
import com.example.jsfservice.compare.thread.DeletedNodeAnalyzer;
import com.example.jsfservice.compare.thread.UnchangedNodeAnalyzer;
import com.example.jsfservice.dom.Node;
import com.example.jsfservice.dto.compare.CompareResponse;
import com.example.jsfservice.utils.CompareUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class CompareServiceImpl implements CompareService {

    @Autowired
    CompareUtils compareUtils;

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public CompareResponse getCompare(List<Node> oldVer, List<Node> newVer) throws ExecutionException, InterruptedException {
        Thread.sleep(500);
        Future<List<Node>> addedNodeFuture, deletedNodesFuture;
        Future<Set<Node>> changedNodesFuture, unchangedNodesFuture;
        addedNodeFuture = THREAD_POOL.submit(new AddedNodeAnalyzer(oldVer, newVer));
        deletedNodesFuture = THREAD_POOL.submit(new DeletedNodeAnalyzer(oldVer, newVer));
        changedNodesFuture = THREAD_POOL.submit(new ChangedNodeAnalyzer(oldVer, newVer));
        unchangedNodesFuture = THREAD_POOL.submit(new UnchangedNodeAnalyzer(oldVer, newVer));

        List<Node> addedNode = addedNodeFuture.get();
        List<Node> deletedNodes = deletedNodesFuture.get();
        Set<Node> changedNodes = changedNodesFuture.get();
        Set<Node> unchangedNodes = unchangedNodesFuture.get();

        return new CompareResponse(addedNode, deletedNodes, new ArrayList<>(changedNodes), new ArrayList<>(unchangedNodes), newVer);

    }

}
