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

        var read = false;

        while (!read) {

            var line = sc.nextLine();

            Option option = null;

            try {
                option = parseOption(line);
                read = true;
            } catch (IllegalArgumentException e) {
                System.out.println(String.format("Error on read option: %s",e.getMessage()));
                continue;
            }

            switch (option.option) {
                case 1:
                    routeMap = routeMap.from(option.stops.toArray(TrainStop[]::new));
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
                case 8:
                    routeMap = routeMap.sequence(option.stops.toArray(TrainStop[]::new));
                    break;
                case 9:
                    clearConfig();
                    break;
                case 0:
                    showResults();
                    break;
                default:
                    System.out.println("Invalid Option");
            }
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

        System.out.println("Enter the option number as O p..., O is the option and p are the required parameters");
        System.out.println("Example '1 A,B' to begin from A,B");
        System.out.println("Option 1,7 and 8 accept more than one argument, all the others accepts only one argument");

        System.out.println("1 A,B,C... - BEGIN WITH SEQUENCE");
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

    private Option parseOption(String line) throws IllegalArgumentException {

        if (Objects.isNull(line) || line.isBlank())
            throw new IllegalArgumentException("Invalid Option");

        var regexOneNode = "[a-zA-Z]";
        var regexTwoNode = "[a-zA-Z],[a-zA-Z]";
        var regexMultiNode = "(([a-zA-Z],)|([a-zA-Z])$)+";
        var regexNumber = "\\d{1,3}";

        var op = new Option();
        var _op = line.charAt(0);
        line = line.substring(1).strip();

        switch (_op) {

            case '1':
                if (!line.matches(regexMultiNode))
                    throw new IllegalArgumentException("Invalid string. Must provide at least one node");
                op.option = 1;
                op.stops = parser.parseNodes(line, TrainStop::new);
                break;
            case '2':
                if (!line.matches(regexOneNode))
                    throw new IllegalArgumentException("Invalid string. Must provide exactly one node");
                op.option = 2;
                op.stops = parser.parseNodes(line, TrainStop::new);
                break;
            case '3':
                if (!line.matches(regexNumber))
                    throw new IllegalArgumentException("Invalid string. Must provide one number from 0-999");
                op.option = 3;
                op.value = Integer.parseInt(line);
                break;
            case '4':
                if (!line.matches(regexNumber))
                    throw new IllegalArgumentException("Invalid string. Must provide one number from 0-999");
                op.option = 4;
                op.value = Integer.parseInt(line);
                break;
            case '5':
                if (!line.matches(regexNumber))
                    throw new IllegalArgumentException("Invalid string. Must provide one number from 0-999");
                op.option = 5;
                op.value = Integer.parseInt(line);
                break;
            case '6':
                if (!line.matches(regexNumber))
                    throw new IllegalArgumentException("Invalid string. Must provide one number from 0-999");
                op.option = 6;
                op.value = Integer.parseInt(line);
                break;
            case '7':
                if (!line.matches(regexTwoNode))
                    throw new IllegalArgumentException("Invalid string. Must provide two nodes");
                op.option = 7;
                op.stops = parser.parseNodes(line, TrainStop::new);
                break;
            case '8':
                if (!line.matches(regexMultiNode))
                    throw new IllegalArgumentException("Invalid string. Must provide at least one node");
                op.option = 8;
                op.stops = parser.parseNodes(line, TrainStop::new);
                break;
            case '9':
                op.option = 9;
                break;

            case '0':
                op.option = 0;
                break;

            default:
                throw new IllegalArgumentException("Invalid Option");
        }

        return op;
    }



    private class Option {

        int option;

        int value;

        List<TrainStop> stops;

    }

}
