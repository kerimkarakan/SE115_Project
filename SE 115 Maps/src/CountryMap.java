public class CountryMap {
    private City[] cities;
    private Route[] routes;
    private int cityArrayCounter;
    private int routeArrayCounter;

    public CountryMap(int cityNumber, int routeNumber){
        cities = new City[cityNumber];
        routes = new Route[routeNumber];
        cityArrayCounter = 0;
        routeArrayCounter = 0;
    }

    public boolean cityExists(String cityName){
        for(int i = 0; i < cityArrayCounter; i++){
            if(cities[i].getName().equals(cityName)){
                return true;
            }
        }
        return false;
    }
    public void addCity(String name){
        if(cityArrayCounter < cities.length){
            if(cityExists(name)){
                System.out.println("A city with this name already exists. Please try another city name.");
            }
            else {
                cities[cityArrayCounter++] = new City(name);
            }
        }
        else{
            System.out.println("You cannot add more cities. The maximum limit has been reached.");
        }
    }

    public City noCity(){
        return new City("No city");
    }
    public City getCity(String name){
        for(int i = 0; i < cityArrayCounter; i++){
            if(cities[i].getName().equals(name)){
                return cities[i];
            }
        }
        return noCity();
    }

    public void addRoute(String city1Name, String city2Name, int time){
        City city1 = getCity(city1Name);
        City city2 = getCity(city2Name);
        if(!(noCity().getName().equals(city1.getName())) && !(noCity().getName().equals(city2.getName()))){
            if(routeArrayCounter < routes.length){
                routes[routeArrayCounter++] = new Route(city1, city2, time);
            }
            else{
                System.out.println("You cannot add more routes. The maximum limit has been reached");
            }
        }
        else{
            System.out.println("One or both of the cities not found. The route cannot be added.");
        }
    }

    public City[] getCities(){
        return cities;
    }

    public Route[] getRoutes(){
        return routes;
    }
}
