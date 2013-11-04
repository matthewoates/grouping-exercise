package grouper;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.*;
import java.util.*;

public class Parser {
    private String[] header;
    private Entry[] entries;

    Parser(String inputFile) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(inputFile));

        List<String[]> data = reader.readAll();
        header = data.get(0);
        entries = new Entry[data.size() - 1];

        for (int i = 1; i < data.size(); i++) {
            entries[i - 1] = new Entry(header, data.get(i));
        }
    }

    Entry[] getEntries() {
        return entries;
    }

    public void writeResults() {
        // all matching has been done. Now, print out the results
        CSVWriter writer = new CSVWriter(new PrintWriter(System.out));

        writer.writeNext(header);

        int id = 1;

        for (Entry entry : entries) {
            if (entry.hasMatches() && !entry.idIsSet()) {
                entry.setID(id);
                id += 1;
            }
            writer.writeNext(entry.getOutputData());
        }

        try {
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
