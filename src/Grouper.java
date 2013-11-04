package grouper;

public class Grouper {
    public static void main(String[] args) throws Exception {
        int exitCode = 0;

        if (args.length == 1 && args[0].equals("-test")) {
            try {
                TestRunner.runTests();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (args.length == 2) {
            // [input_file] [matching_type]
            String inputFile = args[0];
            MatchType m = MatchType.fromString(args[1]);

            group(inputFile, m);
        } else {
            usage();
            exitCode = 1;
        }

        System.exit(exitCode);
    }

    public static void group(String path, MatchType matchType) throws Exception {
        System.out.println("grouping " + path + "." + matchType.getFlag());

        Parser parser = new Parser(path);

        parser.findMatches(matchType);

        parser.writeResults();
    }

    private static void usage() {
        System.out.println("usage:");
        System.out.println("    make run [input_file] [matching_type]");
        System.out.println("\nSupported Matching types:");
        for (MatchType m : MatchType.values()) {
            System.out.println("    " + m.getFlag());
        }
        System.out.println("\n");
    }
}
