package grouper;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.*;
import java.util.*;

public class Parser {
    private Entry[] entries;

    Parser(String inputFile) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(inputFile));

        List<String[]> data = reader.readAll();
        String[] header = data.get(0);
        entries = new Entry[data.size() - 1];

        for (int i = 1; i < data.size(); i++) {
            entries[i - 1] = new Entry(header, data.get(i));
        }
    }

    //ArrayList<HashMap<String, ArrayList<String>>> getEntries() {
    Entry[] getEntries() {
        return entries;
    }
}
