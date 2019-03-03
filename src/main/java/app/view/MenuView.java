package app.view;

import app.graph.DFSPathTraversal;
import app.graph.MinimumPathBuilder;
import app.graph.PathTraversalConfigurationBuilder;
import app.route_map.*;

import java.util.List;
import java.util.Scanner;

public class MenuView {

//    private RouteMap<TrainStop> routeMap;
//
//    private Scanner sc = new Scanner(System.in);
//
//    private PathTraversalConfigurationBuilder configBuilder;
//
//
//    public MenuView() {
//
//    }
//
//    public void show() {
//
//        showHelp();
//
//        var graph = readGraph();
//
//        var builder = new RouteMapBuilder<TrainStop>(new MinimumPathBuilder<>(),
//                new DFSPathTraversal<>());
//
////        builder.addHops(graph);
//
//        routeMap = builder.build();
//
//        System.out.println("ROUTE MAP READY!");
//
//        configBuilder = PathTraversalConfigurationBuilder.defaultConfiguration();
//
//        while (true) {
//
//            showCurrentConfig();
//            showOptions();
//            readOptions();
//
//        }
//
//    }
//
//    private void showHelp() {
//
//        System.out.println("WELCOME TO TRAIN MANAGEMENT");
//
//        System.out.println("The first thing you need to do before " +
//                "utilizing the program is to provide a string containing the graph route");
//
//        System.out.println();
//        System.out.println("The graph route is entered in the form of [<x><y>N]* (accepts comma too). " +
//                "Where x and y are 1 letter node names and N is a integer");
//
//        System.out.println("For example AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
//        System.out.println();
//
//        System.out.println("After this, you can choose options to traverse the graph.");
//
//    }
//
//    private void readOptions() {
//
//        var option = sc.nextLine();
//
//        TrainStop ts;
//        switch (option.charAt(0)) {
//
//            case '1':
//                configBuilder = configBuilder.beginWith(StopParser.parseStops(option).get(0));
//                break;
//
//            case '2':
//                configBuilder = configBuilder.endWith(StopParser.parseStops(option).get(0));
//                break;
//            case '3':
//                configBuilder = configBuilder.withMaximumCost(Integer.parseInt(option.substring(2)));
//                break;
//            case '4':
//                configBuilder = configBuilder.withMaximumHops(Integer.parseInt(option.substring(2)));
//                break;
//            case '5':
//                configBuilder = configBuilder.withExactlyCost(Integer.parseInt(option.substring(2)));
//                break;
//            case '6':
//                configBuilder = configBuilder.withExactlyHops(Integer.parseInt(option.substring(2)));
//                break;
////            case '7':
////                configBuilder = configBuilder.withExactlyHops(Integer.parseInt(option.substring(1)));
////                break;
//
//            case '7':
//                showMinimumWeight(option);
//                break;
//
//            case '8':
//                configBuilder = configBuilder.withExactlySequence(StopParser.parseStops(option).toArray());
//                break;
//
//            case '9':
//                clearConfig();
//                break;
//
//            case '0':
//                showResults();
//                break;
//
//        }
//
//    }
//
//    private void showMinimumWeight(String option) {
//
//        var from = StopParser.parseStops(option).get(0);
//        var to = StopParser.parseStops(option).get(1);
//
//        System.out.println(String.format("Distance from %s to %s = %d", from, to, routeMap.minimumDistanceFrom(from, to)));
//
//    }
//
//    private void clearConfig() {
//        this.configBuilder = PathTraversalConfigurationBuilder.emptyConfiguration();
//    }
//
//    private void showResults() {
//
//        try {
//
//            System.out.println("-----------------------------------------");
//
//            List<Route<TrainStop>> routes = routeMap.getRoutes(configBuilder.build());
//
//            if (routes.isEmpty())
//                System.out.println("NO SUCH ROUTE");
//            else
//                routes.forEach(System.out::println);
//
//            System.out.println("-----------------------------------------");
//
//        } catch (IllegalArgumentException e) {
//            System.out.println("Error when creating route - " + e.getMessage());
//            e.printStackTrace();
//        }
//
//    }
//
//    private List<StopHop<TrainStop>> readGraph() {
//
//        var graph = sc.nextLine();
//
//        return StopParser.parse(graph);
//
//    }
//
//    private void showCurrentConfig() {
//        System.out.println();
//        System.out.println("-------------------------");
//        System.out.println("Current Config");
//        System.out.println(configBuilder);
//        System.out.println("-------------------------");
//    }
//
//    private void showOptions() {
//
//        System.out.println("Enter the option number as N p, N is the option and p the required parameter");
//        System.out.println("Example '1 A' to begin from A");
//
//        System.out.println("EXCEPT BEGIN WITH SEQUENCE WHO IS THE FORMAT OF '7 A B C...'");
//        System.out.println("EXCEPT FROM TO WHO IS THE FORMAT OF '7 A B '");
//
//        System.out.println("1 - BEGIN WITH ");
//        System.out.println("2 - END WITH ");
//        System.out.println("3 - MAXIMUM DISTANCE ");
//        System.out.println("4 - MAXIMUM STOPS ");
//        System.out.println("5 - EXACTLY DISTANCE ");
//        System.out.println("6 - EXACTLY STOPS");
////        System.out.println("7 [P...] - BEGIN WITH SEQUENCE");
//        System.out.println("7 A B - MINIMUM DISTANCE FROM TO");
//        System.out.println("8 A B C... - SCTRICLTY PATH");
//        System.out.println("9 - CLEAR CONFIGURATION");
//        System.out.println("0 - EXECUTE");
//
//    }


}
