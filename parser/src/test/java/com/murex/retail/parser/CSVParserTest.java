package com.murex.retail.parser;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVParserTest {
    private static final String TEST_PATH = "Test.csv";
    private static final String INCORRECT_INVENTORY_PATH = "FakeFile.csv";

    @Test
    public void testIncorrectPathThrowsException() {
        assertThrows(FileNotFoundException.class, () -> CSVParser.parseCSV(INCORRECT_INVENTORY_PATH, true));
    }

    @Test
    public void testParseCSVRemoveHeaderFalse() throws IOException, URISyntaxException {
        String[] lineAfterParsing = CSVParser.parseCSV(TEST_PATH, false).get(0);
        String[] expectedFields = new String[]{
                "ID",
                "Category",
                "Name",
                "Brand",
                "Product Line",
                "Number Of Cores",
                "Processor Clock Speed",
                "Graphic Clock Speed",
                "Dimension",
                "Resolution",
                "Color",
                "Interface",
                "Size",
                "Price",
                "Quantity"
        };

        assertArrayEquals(expectedFields, lineAfterParsing);
    }

    @Test
    public void testParseCSVRemoveHeaderTrue() throws IOException, URISyntaxException {
        String[] lineAfterParsing = CSVParser.parseCSV(TEST_PATH, true).get(0);
        String[] expectedFields = new String[]{
                "70d0c37e-634e-4a68-8862-0ba44f216f3b",
                "CPU",
                "Intel Core i7-8809G",
                "Intel",
                "Core i7",
                "4",
                "3.10 GHz",
                "1.20 GHz",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "150",
                "25"
        };

        assertArrayEquals(expectedFields, lineAfterParsing);
    }
}
