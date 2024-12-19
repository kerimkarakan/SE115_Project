public class TestingProgram {

    public static void main(String[] args) {
        String inputFile = args[0];
        String outputFile = args[1];
        WayFinder wayFinder = new WayFinder();
        wayFinder.getMapData(inputFile, outputFile);
    }
}
