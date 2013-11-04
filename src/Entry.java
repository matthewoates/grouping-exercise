package grouper;

import java.util.*;

public class Entry {
    private String[] rawData; // preserve the unsanatized data for future output
    private HashMap<String, ArrayList<String>> data;
    private ArrayList<Entry> matches = new ArrayList<Entry>(); // holds symmetrical links to other matching entries
    private int id = 0; // the matched ID. 0 means no matches were found

    private static String getFieldName(String rawName) {
        // some data types span multiple fields
        // ie: Name1, Name2
        // Here, we will chop off the number at the end,
        // resulting in more consistent field names
        int end = rawName.length() - 1;

        while (end > 0 && Character.isDigit(rawName.charAt(end))) {
            end -= 1;
        }

        assert(end >= 0);

        return rawName.substring(0, end + 1);
    }

    private static String sanitizeField(String fieldType, String field) {
        // some fields have unnecessary formatting, and other junk characters
        // this function chooses the appropriate algorithm to give us just the info we need
        // in most cases, a trim() will suffice
        String result = null;

        if (fieldType.equals(MatchType.samePhone.getFieldName())) {
            result = sanitizePhone(field);
        } else {
            result = field.trim();
        }

        return result;
    }

    private static String sanitizePhone(String field) {
        // phone numbers can be in many formats,
        // so we just grab the last 10 digits and ignore all formatting
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
                // only add non-empty strings to prevent missing fields from different entries matching each other
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
        // this can only be called before the ID is set for both nodes
        // create a symmetrical link between this and entry
        assert(this.id == 0 && entry.id == 0);

        this.matches.add(entry);
        entry.matches.add(this);
    }

    public void setID(int id) {
        // recursively resolve all matching nodes
        // recursion will terminate when idIsSet() is true for all matching nodes
        assert(!idIsSet() && hasMatches());

        this.id = id;

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
