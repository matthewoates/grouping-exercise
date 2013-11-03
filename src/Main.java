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

public class Main {
    public static void main(String[] args) {
        // for temporary testing, load a csv file at the path specified by the first arg
        try {
            loadFile(args[0]);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void loadFile(String path) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(path));
        String[] nextLine = null;

        while ((nextLine = reader.readNext()) != null) {
            System.out.println(join(nextLine, ", "));
        }
    }

    private static String join(String[] arr, String glue) {
        // quick and dirty method for testing purposes
        // O(N^2) complexity due to the immutable nature of the Java String
        String result = "";

        for (int i = 0; i < arr.length; i++) {
            if (i != 0) {
                result += glue;
            }

            result += arr[i];
        }

        return result;
    }
}
