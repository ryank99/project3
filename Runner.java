

public class Runner {

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            throw(new Error("Invalid number of program arguments"));
        }
        new Parser(args[0], args[1]);
    }

}
