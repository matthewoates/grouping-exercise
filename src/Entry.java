package grouper;

public class Entry {
    Entry(String[] header, String[] fields) {
        System.out.println("parsing entry!");
        for (int i = 0; i < fields.length; i++) {
            System.out.print(fields[i] + ", ");
        }
        System.out.println("");
    }
}
