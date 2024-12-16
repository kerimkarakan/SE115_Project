import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Formatter;
import java.io.FileWriter;

public class WayFinder {
    private CountryMap countryMap;

    public WayFinder(){
        countryMap = new CountryMap(0,0);
    }

    public void getMapData(String filename){
        Scanner reader = null;
        int fileLineNum = 0;
        try{
            reader = new Scanner(Paths.get(filename));

            fileLineNum++;
            int numberCities = Integer.parseInt(reader.nextLine().trim());

            fileLineNum++;
            String[] cities = reader.nextLine().trim().split(" ");
            for(String city : cities){
                countryMap.addCity(city);
            }

            fileLineNum++;
            int numberOfRoutes = Integer.parseInt(reader.nextLine().trim());

            for(int i = 0; i < numberOfRoutes; i++){
                fileLineNum++;
                String[] routes_w_time = reader.nextLine().trim().split(" ");
                countryMap.addRoute(routes_w_time[0], routes_w_time[1], Integer.parseInt(routes_w_time[2]));
            }

            fileLineNum++;
            String[] startEndCity = reader.nextLine().trim().split(" ");
            String startCity = startEndCity[0];
            String endCity = startEndCity[1];

            System.out.println("File read is successful!");
            findFastestWay(startCity, endCity);
        }
        catch(IOException e){
            System.err.println("File is not read successfully.");
        }
        catch(NumberFormatException e){
            System.err.println("The number cannot be formatted at the line "+fileLineNum);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.err.println("Some data is missing at the line "+fileLineNum);
        }
        finally{
            if(reader != null){
                reader.close();
            }
        }
    }

    public int getIndex(City city){
        for(int i = 0; i < countryMap.getCities().length; i++){
            if(countryMap.getCities()[i].equals(city)){
                return i;
            }
        }
        return -1;
    }

    public int getMinDistanceCity(int[] distances, boolean[] visited){
        int minDistance = Integer.MAX_VALUE;
        int currentCityIndex = -1;

        for(int i = 0; i < distances.length; i++){
            if(!visited[i] && distances[i] < minDistance){
                minDistance = distances[i];
                currentCityIndex = i;
            }
        }
        return currentCityIndex;
    }

    public void findFastestWay(String startC, String endC){
        City startCity = countryMap.getCity(startC);
        City endCity = countryMap.getCity(endC);
        if(startCity == null || endCity == null){
            System.out.println("Start or end city is no found.");
            return;
        }

        int startCityIndex = getIndex(startCity);
        int endCityIndex = getIndex(endCity);
        if(startCityIndex == -1 ||endCityIndex == -1){
            System.out.println("Start or end city index is not found.");
            return;
        }

        int numberOfCities = countryMap.getCities().length;
        int[] distances = new int[numberOfCities];
        boolean[] visited = new boolean[numberOfCities];
        City[] previousCity = new City[numberOfCities];

        for(int i = 0; i < numberOfCities; i++){
            distances[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        distances[startCityIndex] = 0;

        for(int i = 0; i < numberOfCities; i++){
            int currentCityIndex = getMinDistanceCity(distances, visited);
            if(currentCityIndex == -1){
                System.out.println("All cities have been visited or no city is reachable");
                break;
            }
            visited[currentCityIndex] = true;

            for(Route route: countryMap.getRoutes()){
                if(route.getCity1().equals(countryMap.getCities()[currentCityIndex]) && !visited[getIndex(route.getCity2())]){
                    int potentialNewDistance = distances[currentCityIndex] + route.getTime();
                    if(potentialNewDistance < distances[getIndex(route.getCity2())]) {
                        distances[getIndex(route.getCity2())] = potentialNewDistance;
                        previousCity[getIndex(route.getCity2())] = countryMap.getCities()[currentCityIndex];
                    }
                }
            }
        }

        printFastestWay(previousCity, startCity, endCity, distances[endCityIndex]);
    }

    public void printFastestWay(City[] previousCity, City startCity, City endCity, int totalTime){
        if(totalTime == Integer.MAX_VALUE){
            System.out.println("No path exists between " + startCity.getName() + " and " + endCity.getName());
            return;
        }

        StringBuilder fastestWayPath = new StringBuilder();
        City currentCity = endCity;

        while(currentCity != null){
            fastestWayPath.insert(0, currentCity.getName() + " -> ");
            currentCity = previousCity[getIndex(currentCity)];
        }

        fastestWayPath.delete(fastestWayPath.length() - 4, fastestWayPath.length());
        String output = "Fastest Way: " + fastestWayPath.toString() + "\nTotal Time: " + totalTime + " min";

        System.out.println(output);

        FileWriter fileWriter = null;
        Formatter formatter = null;
        try {
            fileWriter = new FileWriter("output.txt");
            formatter = new Formatter(fileWriter);
            formatter.format("%s", output);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the output file.");
        } finally {
            if (formatter != null) {
                formatter.close();
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    System.err.println("An error occurred while closing FileWriter.");
                }
            }
        }
    }
}
