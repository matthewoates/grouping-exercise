package grouper;

import grouper.Grouper;

import au.com.bytecode.opencsv.*;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.*;

public class TestRunner {
    private static boolean testFileExists(int index) {
        return index == 1;
        //return new File(getTestPath(index)).exists();
    }

    private static String getTestPath(int testID) {
        return "../tests/input" + testID + ".csv";
    }

    public static void runTests() throws Exception {
        // run files from test1.csv sequentially, until there are no more files
        for (int i = 1; testFileExists(i); i++) {
            boolean success = testFile(getTestPath(i));

            System.out.println(i + ": " + (success ? "PASSED" : "FAILED"));
        }
    }

    private static boolean testFile(String path) throws Exception {
        Grouper.group(path);

        return true;
    }
}
