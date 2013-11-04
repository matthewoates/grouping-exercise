package grouper;

import java.util.*;

public class Entry {
    private String[] rawData;
    private HashMap<String, ArrayList<String>> data;
    private ArrayList<Entry> matches = new ArrayList<Entry>();
    private int id = 0;

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

    private static String sanitizeField(String fieldType, String field) {
        String result = null;

        if (fieldType.equals(MatchType.samePhone.getFieldName())) {
            result = sanitizePhone(field);
        } else {
            result = field.trim();
        }

        return result;
    }

    private static String sanitizePhone(String field) {
        String digits = field.replaceAll("[^0-9]", "");

        return digits.length() > 10 ? digits.substring(digits.length() - 10, digits.length()) : digits;
    }

    Entry(String[] header, String[] fields) {
        assert(header.length == fields.length);

        rawData = fields;
        data = new HashMap<String, ArrayList<String>>();

        for (int i = 0; i < header.length; i++) {
            String fieldName = getFieldName(header[i]);

            if (!data.containsKey(fieldName)) {
                data.put(fieldName, new ArrayList<String>());
            }

            String sanitizedField = sanitizeField(header[i], fields[i]);

            if (sanitizedField != null && sanitizedField.length() > 0) {
                data.get(fieldName).add(sanitizeField(header[i], fields[i]));
            }
        }
    }

    public ArrayList<String> getField(String field) {
        return data.get(field);
    }

    public String[] getOutputData() {
        // append the id that has been matched
        String[] result = new String[rawData.length + 1];

        result[0] = Integer.toString(id);

        for (int i = 0; i < rawData.length; i++) {
            result[i + 1] = rawData[i];
        }

        return result;
    }

    public void addMatch(Entry entry) {
        assert(this.id == 0 && entry.id == 0);

        this.matches.add(entry);
        entry.matches.add(this);
    }

    public void setID(int id) {
        assert(!idIsSet() && hasMatches());

        this.id = id;

        // recursively set the id of all other matching entries
        for (Entry entry : matches) {
            if (!entry.idIsSet()) {
                entry.setID(id);
            }
        }
    }

    public boolean hasMatches() {
        return matches.size() > 0;
    }

    public boolean idIsSet() {
        return id != 0;
    }
}
