import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class WayFinder {
    private CountryMap countryMap;

    public WayFinder(int numberCities, int numberRoutes){
        countryMap = new CountryMap(numberCities, numberRoutes);
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
            int numberRoutes = Integer.parseInt(reader.nextLine().trim());

            for(int i = 0; i < numberRoutes; i++){
                fileLineNum++;
                String[] routes_w_time = reader.nextLine().trim().split(" ");
                countryMap.addRoute(routes_w_time[0], routes_w_time[1], Integer.parseInt(routes_w_time[2]));
            }

            fileLineNum++;
            String[] startEndCity = reader.nextLine().trim().split(" ");
            String startCity = startEndCity[0];
            String endCity = startEndCity[1];

            System.out.println("File is successfully read!");
        }
        catch(IOException e){
            System.err.println("File is not successfully read!");
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
}
