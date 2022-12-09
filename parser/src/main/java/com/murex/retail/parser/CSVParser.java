package com.murex.retail.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CSVParser {
    private static final Logger LOG = LogManager.getLogger(CSVParser.class);

    public static List<String[]> parseCSV(String path, boolean removeHeader) throws IOException, URISyntaxException {
        LOG.info("Reading CSV file...");
        URL resource = CSVParser.class.getClassLoader().getResource(path);
        if (resource == null) {
            throw new FileNotFoundException("Unable to locate file at: " + path);
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get(resource.toURI()))) {
            List<String[]> parsedLines = new ArrayList<>();
            String line;
            if (removeHeader) {
                line = br.readLine();
            }
            while ((line = br.readLine()) != null) {
                parsedLines.add(Stream.of(line.split(","))
                        .map(str -> str.trim().strip())
                        .toArray(String[]::new));
            }
            LOG.info("CSV file read and parsed.");
            return parsedLines;
        }
    }
}
