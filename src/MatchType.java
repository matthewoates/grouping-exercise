package grouper;

public enum MatchType {
    sameEmail("same_email"),
    samePhone("same_phone");

    private String flag;

    MatchType(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
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
