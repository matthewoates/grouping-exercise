package grouper;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.*;
import java.util.*;

import grouper.*;

public class Parser {
    private String[] header;
    private Entry[] entries;

    Parser(String inputFile) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(inputFile), getDelimiter(inputFile));

        List<String[]> data = reader.readAll();
        header = data.get(0);
        entries = new Entry[data.size() - 1];

        for (int i = 1; i < data.size(); i++) {
            entries[i - 1] = new Entry(header, data.get(i));
        }
    }

    private char getDelimiter(String inputFile) throws Exception {
        // CSV files can use a tab as a delimiter as well
        Scanner s = new Scanner(new File(inputFile));
        String firstLine = "";

        if (s.hasNextLine()) {
            firstLine = s.nextLine();
        }

        boolean tabDelimiter = (firstLine.indexOf(",") == -1 && firstLine.indexOf("\t") != -1);

        return tabDelimiter ? '\t' : ',';
    }

    void findMatches(MatchType m) {
        HashMap<String, Entry> anchors = new HashMap<String, Entry>();

        for (Entry entry : entries) {
            ArrayList<String> fields = entry.getField(m.getFieldName());

            for (String field : fields) {
                if (anchors.containsKey(field)) {
                    // we have a match!
                    entry.addMatch(anchors.get(field));
                } else {
                    // push the entry in there so others can find it
                    anchors.put(field, entry);
                }
            }
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
