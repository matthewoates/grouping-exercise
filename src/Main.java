public class Main {
    public static void main(String[] args) {
        System.out.println("it works!");

        for (int i = 0; i < args.length; i++) {
            System.out.println("arg " + i + ": " + args[i]);
        }
    }
}
