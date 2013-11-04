package grouper;

public enum MatchType {
    sameEmail("same_email"),
    samePhone("same_phone");

    private String text;

    MatchType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static MatchType fromString(String text) {
        MatchType result = null;

        for (MatchType m : MatchType.values()) {
            if (text.equalsIgnoreCase(m.text)) {
                return m;
            }
        }

        return result;
    }
}
