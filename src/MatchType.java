package grouper;

public enum MatchType {
    sameEmail("same_email", "Email"),
    samePhone("same_phone", "Phone");

    private String flag;
    private String fieldName;

    MatchType(String flag, String fieldName) {
        this.flag = flag;
        this.fieldName = fieldName;
    }

    public String getFlag() {
        return flag;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static MatchType fromString(String flag) {
        MatchType result = null;

        for (MatchType m : MatchType.values()) {
            if (flag.equalsIgnoreCase(m.flag)) {
                return m;
            }
        }

        return result;
    }
}
