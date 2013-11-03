package grouper;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Grouper {
    public static void main(String[] args) {
        int exitCode = 0;

        if (args.length == 1 && args[0].equals("-test")) {
            try {
                TestRunner.runTests();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (args.length == 2) {
            // [input_file] [matching_type]
        } else {
            System.out.println("bad usage");
            exitCode = 1;
        }

        System.exit(exitCode);
    }

    public static void group(String path) {
        System.out.println("grouping " + path + ".");
    }
}
