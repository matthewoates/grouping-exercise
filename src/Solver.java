package grouper;

import au.com.bytecode.opencsv.*;
import java.io.*;
import java.util.*;


public class Solver {
    private String[] header;
    private Entry[] entries;

    public Solver(String inputFile) throws Exception {
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

    public void findMatches(MatchType m) {
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

    public Entry[] getEntries() {
        return entries;
    }

    public void writeResults(Writer w) {
        // all matching has been done. Now, print out the results
        CSVWriter writer = new CSVWriter(w);

        writer.writeNext(prependElement(header, "ID"));

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

    private String[] prependElement(String[] arr, String element) {
        String[] result = new String[arr.length + 1];

        result[0] = element;

        for (int i = 0; i < arr.length; i++) {
            result[i + 1] = arr[i];
        }

        return result;
    }
}
