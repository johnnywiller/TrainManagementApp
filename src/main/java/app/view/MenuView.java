package app.view;

import app.graph.DFSPathTraversal;
import app.graph.DefaultParser;
import app.graph.Interface.GraphParser;
import app.graph.MinimumPathBuilder;
import app.routemap.Route;
import app.routemap.RouteMap;
import app.routemap.RouteMapBuilder;
import app.routemap.TrainStop;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MenuView {

    private RouteMap<TrainStop> routeMap;

    private Scanner sc;

    private GraphParser<TrainStop> parser;

    public MenuView() {

        this.sc = new Scanner(System.in);
        this.parser = new DefaultParser<>();
    }

    public void show() {

        showHelp();

        buildRouteMap();

        while (true) {

            showCurrentConfig();
            showOptions();
            readOptions();
        }

    }

    private void buildRouteMap() {

        var built = false;

        var builder = new RouteMapBuilder<TrainStop>(new MinimumPathBuilder<>(),
                new DFSPathTraversal<>());

        while(!built) {

            var graph = readGraph();

            try {
                builder = builder.fromString(graph, TrainStop::new);
                this.routeMap = builder.build();
                built = true;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("Invalid graph string:" + e.getMessage());
            }
        }

        System.out.println("ROUTE MAP READY!");

    }

    private void showHelp() {

        System.out.println("WELCOME TO TRAIN MANAGEMENT");

        System.out.println("The first thing you need to do before " +
                "utilizing the program is to provide a string containing the graph route");

        System.out.println();
        System.out.println("The graph route is entered in the form of (<x><y>N,)*. " +
                "Where x and y are 1 letter node names and N is a number (0-999), comma between groups are mandatory");

        System.out.println("For example AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        System.out.println();

        System.out.println("After this, you can choose options to traverse the graph.");

    }

    private void readOptions() {

        var line = sc.nextLine();

        var option = parseOption(line);

        switch (option.option) {
            case 1:
                routeMap = routeMap.from(option.stops.get(0));
                break;
            case 2:
                routeMap = routeMap.to(option.stops.get(0));
                break;
            case 3:
                routeMap = routeMap.maxDistance(option.value);
                break;
            case 4:
                routeMap = routeMap.maxStops(option.value);
                break;
            case 5:
                routeMap = routeMap.exactlyDistance(option.value);
                break;
            case 6:
                routeMap = routeMap.exactlyStops(option.value);
                break;
            case 7:
                showMinimumWeight(option.stops.get(0), option.stops.get(1));
                break;
            case '8':
                routeMap = routeMap.sequence(option.stops.toArray(TrainStop[]::new));
                break;
            case '9':
                clearConfig();
                break;
            case '0':
                showResults();
                break;
            default:
                System.out.println("Invalid Option");
        }
    }

    private void showMinimumWeight(TrainStop from, TrainStop to) {
        System.out.println(String.format("Distance from %s to %s = %d", from, to, routeMap.minimumDistanceFrom(from, to)));
    }

    private void clearConfig() {
        this.routeMap.resetConfig();
    }

    private void showResults() {

        try {

            System.out.println("-----------------------------------------");

            List<Route<TrainStop>> routes = routeMap.get();

            if (routes.isEmpty())
                System.out.println("NO SUCH ROUTE");
            else
                routes.forEach(System.out::println);

            System.out.println("-----------------------------------------");

        } catch (IllegalArgumentException e) {
            System.out.println("Error when creating route - " + e.getMessage());
            e.printStackTrace();
        }

    }

    private String readGraph() {
        return sc.nextLine();
    }

    private void showCurrentConfig() {
        System.out.println();
        System.out.println("-------------------------");
        System.out.println("Current Config:");
        System.out.println();
        System.out.println(routeMap.printCurrentConfig());
        System.out.println("-------------------------");
    }

    private void showOptions() {

        System.out.println("Enter the option number as N p, N is the option and p the required parameter");
        System.out.println("Example '1 A' to begin from A");
        System.out.println("Option 7 and 8 accept more than one argument");

        System.out.println("1 - BEGIN WITH");
        System.out.println("2 - END WITH");
        System.out.println("3 - MAXIMUM DISTANCE");
        System.out.println("4 - MAXIMUM STOPS");
        System.out.println("5 - EXACTLY DISTANCE");
        System.out.println("6 - EXACTLY STOPS");
        System.out.println("7 A,B - MINIMUM DISTANCE FROM TO");
        System.out.println("8 A,B,C... - SCTRICLTY PATH");
        System.out.println("9 - CLEAR CONFIGURATION");
        System.out.println("0 - EXECUTE");

    }

    private Option parseOption(String line) {

        if (Objects.isNull(line) || line.isBlank())
            throw new IllegalArgumentException("Invalid Option");

        var op = new Option();

        switch (line.charAt(0)) {

            case '1':
                op.option = 1;
                op.stops = parser.parseNodes(line.substring(1), TrainStop::new);
                break;
            case '2':
                op.option = 2;
                op.stops = parser.parseNodes(line.substring(1), TrainStop::new);
                break;
            case '3':
                op.option = 3;
                op.value = Integer.parseInt(line.substring(2));
                break;
            case '4':
                op.option = 4;
                op.value = Integer.parseInt(line.substring(2));
                break;
            case '5':
                op.option = 5;
                op.value = Integer.parseInt(line.substring(2));
                break;
            case '6':
                op.option = 6;
                op.value = Integer.parseInt(line.substring(2));
                break;
            case '7':
                op.option = 7;
                op.stops = parser.parseNodes(line.substring(1), TrainStop::new);
                break;
            case '8':
                op.option = 8;
                op.stops = parser.parseNodes(line.substring(1), TrainStop::new);
                break;
            case '9':
                clearConfig();
                break;

            case '0':
                showResults();
                break;

            default:
                System.out.println("Invalid Option");
        }

        return op;
    }



    private class Option {

        int option;

        int value;

        List<TrainStop> stops;

    }

}
