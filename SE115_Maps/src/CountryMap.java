public class CountryMap {
    private City[] cities;
    private Route[] routes;
    private int cityArrayCounter;
    private int routeArrayCounter;

    public CountryMap(int numberOfCities, int numberOfRoutes){
        cities = new City[numberOfCities];
        routes = new Route[numberOfRoutes];
        cityArrayCounter = 0;
        routeArrayCounter = 0;
    }

    public void resizeCityArray(){
        City[] newCities = new City[cities.length * 2];
        System.arraycopy(cities, 0, newCities, 0, cities.length);
        cities = newCities;
    }

    public void resizeRouteArray(){
        Route[] newRoutes = new Route[routes.length * 2];
        System.arraycopy(routes, 0, newRoutes, 0, routes.length);
        routes = newRoutes;
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
        if(cityArrayCounter >= cities.length){
            resizeCityArray();
        }
        if(cityExists(name)){
            System.out.println("A city with this name already exists. Please try another city name.");
            return;
        }

        cities[cityArrayCounter++] = new City(name);
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
        if(routeArrayCounter >= routes.length){
            resizeRouteArray();
        }
        City city1 = getCity(city1Name);
        City city2 = getCity(city2Name);
        if(noCity().getName().equals(city1.getName()) || noCity().getName().equals(city2.getName())){
            System.out.println("One or both of the cities is not found. The route is not added.");
            return;
        }

        routes[routeArrayCounter++] = new Route(city1, city2, time);

    }

    public City[] getCities(){
        return cities;
    }

    public Route[] getRoutes(){
        return routes;
    }

    public int getCityArrayCounter(){
        return cityArrayCounter;
    }

    public int getRouteArrayCounter(){
        return routeArrayCounter;
    }
}
