public class TestingProgram {

    public static void main(String[] args) {

        if(args.length != 2){
            System.out.println("There are no arguments given or more than 2 arguments.\nPlease give exactly 2 arguments to run the program.");
            return;
        }
        String inputFile = args[0];
        String outputFile = args[1];
        WayFinder wayFinder = new WayFinder();
        wayFinder.getMapData(inputFile, outputFile);
    }
}
