public class Route {
    private City city1;
    private City city2;
    private int time;

    public Route(City City1, City City2, int Time){
        city1 = City1;
        city2 = City2;
        time = Time;
    }

    public City getCity1(){
        return city1;
    }

    public City getCity2(){
        return city2;
    }

    public int getTime(){
        return time;
    }
}
