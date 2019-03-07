# TrainManagementApp

## This solution is proposed to the problem one: Trains.

## General Architecture 
This project consists in basically two distinct parts, one for domain classes and view and the other part is more generic and could be reused for other applications.

The domain part are the 'routemap' and the 'view' packages, the other part is on the 'graph' package.

There is also a API to traverse the graph, we call it RouteMap API.

There are four main interfaces:

#### GraphParser: 
Responsable for parsing a graph string, if one is building a new parser, it should implement this interface.
There is a default implementation called DefaultParser
#### IncidenceMatrix: 
Responsable for storing the graph incidences and used by other components to calculate minimum path and traverse the graph.
The default implementation is ListIncidenceMatrix, which stores incidences in a structure like 
HashMap<T, HashSet<IncidenceNode<T>>>
#### MinimumPathBuilder:
Responsable for building the minimum path between vertexes and storing in a incidence matrix.
The default implementation is FloydWarshallMinimumPathBuilder, which obviouslly use Floyd Warshall to compute the minimum path between all nodes, the disadvantage is that we calculate all paths in the initialization. This interface could be used to create other minimumpaths implementations like Dijkstra or A*
#### PathTraversalEngine:
This interface is used to traverse the graph accoring some specific PathTraversalConfiguration. The default implementation is DFSPathTraversal.

There is also an abstract class to represent Vertexes called Vertex. One should extend this class to represent a 'domain vertex', which means a domain class with vertex behavior.

The class used to represent the cities in the problem, is TrainStop wich is a Vertex.

To represent a route between many cities, we used a class named Route, and to find Routes we create a class named RouteMap.

To build a Routemap, is necessary to use a RouteMapBuilder. It's possible to instantiate a RouteMapBuilder with one implementation of MinimumPathBuilder<T> and one of PathTraversalEngine<T> in it's constructor.

RouteMapBuilder has a method called RouteMapBuilder<T> fromString(String graph, Supplier<T> supplier), which is responsible for creating a graph of T elements from a String representation.

The RouteMapBuilder has two IncidenceMatrix because in this proposed solution we use different classes for computing minimumpath and to traverse, and both need a incidence matrix, so two of them are created.

To compute minimum path, we used Floyd Warshall algorithm. We run FW once we call RouteMapBuilder#build() to create a RouteMap. Currently it's not possible to add new nodes once the RouteMap is built, because we would need to rerun FW again, such implementation is on the backlog :-) .

### RouteMap API

We created an api to traverse the graph, there is an option to pass a PathTraversalConfiguration or use the API directly.
If not using the API directly, is needed to use a PathTraversalConfigurationBuilder to build a PathTraversalConfiguration then pass it to RouteMap#getRoutes

Example of api use:

var tsA = new TrainStop("A");
var tsC = new TrainStop("C");
    
var builder = new RouteMapBuilder<TrainStop>(new FloydWarshallMinimumPathBuilder<>(),
    new DFSPathTraversal<>()).fromString("AB1, BC4", TrainStop::new);

var routeMap = builder.build();

List<Route<TrainStop>> routes = routeMap.from(tsA).to(tsC).get();

This list contains the nodes:
A -> B -> C with total Cost: 5

Also example:

routeMap.to(tsC).maxDistance(30).maxStops(5).limit(10)

Would return the first 10 routes with ends with C and contains at most distance 30 OR 5 stops (the first condition is what it gets)

From method also accepts an array of nodes, this way it's possible to configure a route to begin with A,D,C for example.

Other possible methods of RouteMap API:

#### from(T... begin) - begins with some sequence
#### to(T end) - end with determined node
#### maxStops(Integer maxStops) - constains at most stops
#### maxDistance(Integer maxDistance)  - contains at most distance
#### exactlyStops(Integer stops) - contains exactly stops
#### exactlyDistance(Integer distance) - contains exactly distance
#### sequence(T... sequence) - contains exaclty this path
#### limit(Integer limit) - limits the amount of returned routes


## How to run the program
You could run the program directly in an IDE or run with maven.
Use mvn package, and after mvn exec:java, or without maven java -cp target/train-management-app-1.0-SNAPSHOT.jar app.Main
