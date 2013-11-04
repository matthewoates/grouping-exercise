package grouper;

import java.io.*;

public class Grouper {
    public static void main(String[] args) throws Exception {
        if (args.length == 1 && args[0].equals("-test")) {
            TestRunner.runTests();
        } else if (args.length == 2) {
            String inputFile = args[0];
            MatchType matchType = MatchType.fromString(args[1]);

            if (matchType != null) {
                group(inputFile, matchType, new PrintWriter(System.out));
            } else {
                usage();
            }
        } else {
            usage();
        }
    }

    public static void group(String path, MatchType matchType, Writer writer) throws Exception {
        Solver solver = new Solver(path);

        solver.findMatches(matchType);
        solver.writeResults(writer);
    }

    private static void usage() {
        System.out.println("usage:");
        System.out.println("    make run [input_file] [matching_type]");
        System.out.println("\nSupported Matching types:");
        for (MatchType m : MatchType.values()) {
            System.out.println("    " + m.getFlag());
        }
        System.out.println("\n");

        System.exit(1);
    }
}
