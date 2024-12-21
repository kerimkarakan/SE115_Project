import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Formatter;
import java.io.FileWriter;

public class WayFinder {
    private CountryMap countryMap;

    public WayFinder(){
        countryMap = new CountryMap(10,10);
    }

    public void getMapData(String inputFilePath, String outputFilePath){
        Scanner reader = null;
        int fileLineNum = 0;
        try{
            reader = new Scanner(Paths.get(inputFilePath));

            fileLineNum++;
            int numberOfCities = Integer.parseInt(reader.nextLine().trim());

            fileLineNum++;
            String[] cities = reader.nextLine().trim().split(" ");
            if(numberOfCities < cities.length){
                System.out.println("You cannot add more than "+ numberOfCities + " cities.");
                return;
            }
            for(int i = 0; i < numberOfCities; i++){
                countryMap.addCity(cities[i]);
            }

            fileLineNum++;
            int numberOfRoutes = Integer.parseInt(reader.nextLine().trim());

            for(int i = 0; i < numberOfRoutes; i++){
                fileLineNum++;
                String[] routesWithTime = reader.nextLine().trim().split(" ");
                countryMap.addRoute(routesWithTime[0], routesWithTime[1], Integer.parseInt(routesWithTime[2]));
            }

            fileLineNum++;
            String[] startEndCity = reader.nextLine().trim().split(" ");
            String startCity = startEndCity[0];
            String endCity = startEndCity[1];

            System.out.println("File read is successful!");
            findFastestWay(startCity, endCity, outputFilePath);
        }
        catch(IOException e){
            System.err.println("File is not read successfully.");
        }
        catch(NumberFormatException e){
            System.err.println("Error Line "+ fileLineNum +": "+ "The line is empty or the given number cannot be formatted at the line.");
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.err.println("Error Line "+ fileLineNum +": "+ "Data is missing at the line.");
        }
        catch(NoSuchElementException e){
            System.err.println("There is no data for start and end cities.");
        }
        finally{
            if(reader != null){
                reader.close();
            }
        }
    }

    public int getIndex(City city){
        for(int i = 0; i < countryMap.getCities().length; i++){
            if(countryMap.getCities()[i] != null && countryMap.getCities()[i].equals(city)){
                return i;
            }
        }
        return -1;
    }

    public int getMinDistanceCity(int[] SKnownDistance, boolean[] visited){
        int minDistance = Integer.MAX_VALUE;
        int currentCityIndex = -1;

        for(int i = 0; i < SKnownDistance.length; i++){
            if(!visited[i] && SKnownDistance[i] < minDistance){
                minDistance = SKnownDistance[i];
                currentCityIndex = i;
            }
        }
        return currentCityIndex;
    }

    public void findFastestWay(String startC, String endC, String outputFilePath){
        City startCity = countryMap.getCity(startC);
        City endCity = countryMap.getCity(endC);

        int startCityIndex = getIndex(startCity);
        int endCityIndex = getIndex(endCity);
        if(startCityIndex == -1 ||endCityIndex == -1){
            System.out.println("Start or end city index is not found.");
            return;
        }

        int numberOfCities = countryMap.getCities().length;
        int[] SKnownDistance = new int[numberOfCities];
        boolean[] visited = new boolean[numberOfCities];
        City[] previousCitiesInFWay = new City[numberOfCities];

        for(int i = 0; i < numberOfCities; i++){
            SKnownDistance[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        SKnownDistance[startCityIndex] = 0;

        for(int i = 0; i < numberOfCities; i++){
            int currentCityIndex = getMinDistanceCity(SKnownDistance, visited);
            if(currentCityIndex == -1){
                break;
            }
            visited[currentCityIndex] = true;

            for(Route route: countryMap.getRoutes()){
                if(route != null && route.getCity1().equals(countryMap.getCities()[currentCityIndex]) && !visited[getIndex(route.getCity2())]){
                    int potentialNewDistance = SKnownDistance[currentCityIndex] + route.getTime();
                    if(potentialNewDistance < SKnownDistance[getIndex(route.getCity2())]) {
                        SKnownDistance[getIndex(route.getCity2())] = potentialNewDistance;
                        previousCitiesInFWay[getIndex(route.getCity2())] = countryMap.getCities()[currentCityIndex];
                    }
                }
            }
        }
        if(SKnownDistance[endCityIndex] == Integer.MAX_VALUE){
            System.out.println("No way exists between " + startCity.getName() + " and " + endCity.getName());
            return;
        }

        printFastestWay(previousCitiesInFWay, endCity, SKnownDistance[endCityIndex], outputFilePath);

    }

    public void printFastestWay(City[] previousCitiesInFWay, City endCity, int totalTime, String outputFilePath){

        StringBuilder fastestWayPath = new StringBuilder();
        City currentCity = endCity;

        while(currentCity != null){
            fastestWayPath.insert(0, currentCity.getName() + " -> ");
            currentCity = previousCitiesInFWay[getIndex(currentCity)];
        }

        fastestWayPath.delete(fastestWayPath.length() - 4, fastestWayPath.length());
        String output = "Fastest Way: " + fastestWayPath + "\nTotal Time: " + totalTime + " min";

        System.out.println(output);

        Path outputPath = Paths.get(outputFilePath);

        FileWriter fileWriter = null;
        Formatter formatter = null;
        try{
            fileWriter = new FileWriter(outputPath.toString());
            formatter = new Formatter(fileWriter);
            formatter.format("%s", output);
        }
        catch(IOException e){
            System.err.println("An error occurred while writing to the output file.");
        }
        finally{
            if(formatter != null){
                formatter.close();
            }
            if(fileWriter != null){
                try{
                    fileWriter.close();
                }
                catch(IOException e){
                    System.err.println("An error occurred while closing the file writer.");
                }
            }
        }
    }
}