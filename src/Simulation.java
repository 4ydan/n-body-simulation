public class Simulation {

    public static void main(String[] args) {

        Universe universe = new Universe();
        universe.zoom(0);
        universe.speed(10);
        universe.run(10000);
    }
}
