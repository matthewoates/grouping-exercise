package grouper;

import au.com.bytecode.opencsv.*;
import java.io.*;
import java.util.*;

public class TestRunner {
    private static boolean fileExists(String path) {
        return new File(path).exists();
    }

    private static String getInputFilePath(int testID) {
        return "../tests/input" + testID + ".csv";
    }

    private static String getExpectedOutputFilePath(int testID, MatchType matchType) {
        return "../tests/" + matchType.getFlag() + "/expected" + testID + ".csv";
    }

    public static void runTests() throws Exception {
        // run files from test1.csv sequentially, until there are no more files
        for (int i = 1; fileExists(getInputFilePath(i)); i++) {
            boolean success = testFile(i, MatchType.samePhone);

            System.out.println(i + ": " + (success ? "PASSED" : "FAILED"));
        }
    }

    private static boolean testFile(int index, MatchType matchType) throws Exception {
        String inputFilePath = getInputFilePath(index);
        StringWriter writer = new StringWriter();
        boolean success = true;

        try {
            Grouper.group(inputFilePath, matchType, new PrintWriter(writer));
        } catch (Exception e) {
            success = false;
        }

        if (fileExists(getExpectedOutputFilePath(index, matchType))) {
            outputIsValid(writer.toString(), index, matchType);
        } else {
            System.out.println("omitting output validation for test " + index + " with match type " + matchType.getFieldName());
        }

        return success;
    }

    private static boolean outputIsValid(String output, int testID, MatchType matchType) throws Exception {
        // check the contents of expected.csv against the first column in the output
        boolean success = true;
        String[] outputArray = output.split("\n");

        Scanner scanner = new Scanner(new File(getExpectedOutputFilePath(testID, matchType)));

        int outputLine = 1;

        while (scanner.hasNextLine() && success) {
            int actualID = Integer.parseInt(getFirstQuotedValue(outputArray[outputLine]));

            int expectedID = Integer.parseInt(scanner.nextLine());

            if (expectedID != actualID) {
                System.out.println("Line " + outputLine + ": expected " + expectedID + ". got " + actualID);
                success = false;
            }

            outputLine += 1;
        }

        return success;
    }

    private static String getFirstQuotedValue(String str) {
        return str.split("\"")[1];
    }
}
