package com.example.ciaservice.service;

import com.example.ciaservice.Utility.Exporter;
import com.example.ciaservice.ast.Node;
import com.example.ciaservice.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public void exportToCsv(Response data, HttpServletResponse response) {
        try {
            Writer writer = response.getWriter();
            String[] csvHeader = {"Node ID", "Node Weight"};
            try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                csvPrinter.printRecord(csvHeader[0], csvHeader[1]);
                for (Node node : data.getNodes()) {
                    csvPrinter.printRecord(node.getId(), node.getWeight());
                }
            } catch (IOException e) {
                log.error("Error While writing CSV ", e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportToExcel(Response data, HttpServletResponse response) {
        Exporter exporter = new Exporter(data.getNodes());
        try {
            exporter.export(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
