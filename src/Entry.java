package grouper;

import java.util.*;

public class Entry {
    private HashMap<String, ArrayList<String>> data;

    private static String getFieldName(String rawName) {
        // some data types span multiple fields
        // ie: Name1, Name2
        // Here, we will chop off the number at the end
        int end = rawName.length() - 1;

        while (end > 0 && Character.isDigit(rawName.charAt(end))) {
            end -= 1;
        }

        assert(end >= 0);

        return rawName.substring(0, end + 1);
    }

    Entry(String[] header, String[] fields) {
        assert(header.length == fields.length);

        data = new HashMap<String, ArrayList<String>>();

        for (int i = 0; i < header.length; i++) {
            String fieldName = getFieldName(header[i]);

            if (!data.containsKey(fieldName)) {
                data.put(fieldName, new ArrayList<String>());
            }

            data.get(fieldName).add(fields[i]);
        }
    }

    public ArrayList<String> getField(String field) {
        return data.get(field);
    }
}
