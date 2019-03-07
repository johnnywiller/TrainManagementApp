# TrainManagementApp

## This solution is proposed to the problem one: Trains.

## General Architecture 
This project consists in basically two distinct parts, one for domain classes and view and the other part is more generic and could be reused for other applications.

The domain part are the '**routemap'** and the '**view**' packages, the other part is on the '**graph**' package.

There is also a API to traverse the graph, we call it _RouteMap API_.

There are four main interfaces:

#### GraphParser: 
Responsible for parsing a graph string, if one is building a new parser, it should implement this interface.
There is a default implementation called DefaultParser
#### IncidenceMatrix: 
Responsible for storing the graph incidences and used by other components to calculate minimum path and traverse the graph.
The default implementation is ListIncidenceMatrix, which stores incidences in a structure like 
_HashMap<T, HashSet<IncidenceNode< T >>>_
#### MinimumPathBuilder:
Responsible for building the minimum path between vertexes and storing in a incidence matrix.
The default implementation is _FloydWarshallMinimumPathBuilder_, which obviouslly use Floyd Warshall to compute the minimum path between all nodes, the disadvantage is that we calculate all paths in the initialization. This interface could be used to create other minimumpaths implementations like Dijkstra or A*
#### PathTraversalEngine:
This interface is used to traverse the graph according some specific PathTraversalConfiguration. The default implementation is DFSPathTraversal.


### Route Representation
There is an abstract class to represent Vertexes called _Vertex_. One should extend this class to represent a 'domain vertex', which means a domain class with vertex behavior.

The class used to represent the cities in the problem, is _TrainStop_ wich is a _Vertex_.

To represent a route between many cities, we used a class named _Route_, and to find Routes we create a class named _RouteMap_.

To build a _Routemap_, is necessary to use a _RouteMapBuilder_. It's possible to instantiate a _RouteMapBuilder_ with one implementation of _MinimumPathBuilder_<T> and one of _PathTraversalEngine_<T> in it's constructor.

_RouteMapBuilder_ has a method called `RouteMapBuilder<T> fromString(String graph, Supplier<T> supplier), which is responsible for creating a graph of T elements from a String representation.

The RouteMapBuilder has two IncidenceMatrix because in this proposed solution we use different classes for computing minimumpath and to traverse, and both need a incidence matrix, so two of them are created.

To compute minimum path, we used Floyd Warshall algorithm. We run FW once we call `RouteMapBuilder#build() to create a RouteMap. Currently it's not possible to add new nodes once the RouteMap is built, because we would need to rerun FW again, such implementation is on the backlog :-) .

### RouteMap API

We created an api to traverse the graph, there is an option to pass a PathTraversalConfiguration or use the API directly.
If not using the API directly, is needed to use a PathTraversalConfigurationBuilder to build a PathTraversalConfiguration then pass it to RouteMap#getRoutes

#### Example of API use:

```var tsA = new TrainStop("A");
var tsC = new TrainStop("C");
    
var builder = new RouteMapBuilder<TrainStop>(new FloydWarshallMinimumPathBuilder<>(),
    new DFSPathTraversal<>()).fromString("AB1, BC4", TrainStop::new);

var routeMap = builder.build();

List<Route<TrainStop>> routes = routeMap.from(tsA).to(tsC).get();

This list contains the nodes:
A -> B -> C with total Cost: 5
```
Also example:
```
routeMap.to(tsC).maxDistance(30).maxStops(5).limit(10)
```

Would return the first 10 routes with ends with C and contains at most distance 30 OR 5 stops (the first condition is what it gets)

From method also accepts an array of nodes, this way it's possible to configure a route to begin with A,D,C for example.

Other possible methods of RouteMap API:

```
 from(T... begin) // - begins with some sequence
 to(T end) // - end with determined node
 maxStops(Integer maxStops) // - constains at most stops
 maxDistance(Integer maxDistance)  // - contains at most distance
 exactlyStops(Integer stops) // - contains exactly stops
 exactlyDistance(Integer distance) // - contains exactly distance
 sequence(T... sequence) // - contains exaclty this path
 limit(Integer limit) // - limits the amount of returned routes
```

## How to run the program
You could run the program directly in an IDE or with maven.
Use `mvn package`, and after `java -cp target/train-management-app-1.0-SNAPSHOT.jar app.Main`

#### Example

When you run the program, it should appear:
```
WELCOME TO TRAIN MANAGEMENT
The first thing you need to do before utilizing the program is to provide a string containing the graph route

The graph route is entered in the form of (<x><y>N,)*. Where x and y are 1 letter node names and N is a number (0-999), comma between groups are mandatory
For example AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7

After this, you can choose options to traverse the graph. 
```
Then you should enter some input, example:
```
AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
ROUTE MAP READY!

-------------------------
Current Config:

Maximum cost: 30

-------------------------
Enter the option number as X p..., X is the option and p are the required parameters
Example '1 A,B' to begin from A,B. 'Or 3 10' to show all paths with maximum distance of 10.
Option 1, 7 and 8 accept more than one argument, all the others accepts only one argument
1 A,B,C... - BEGIN WITH SEQUENCE
2 - END WITH
3 - MAXIMUM DISTANCE
4 - MAXIMUM STOPS
5 - EXACTLY DISTANCE
6 - EXACTLY STOPS
7 A,B - MINIMUM DISTANCE FROM TO
8 A,B,C... - SCTRICLTY PATH
9 - CLEAR CONFIGURATION
0 - EXECUTE
```

Enter `1 A`, then `2 C` and you should see:

```
---------------RESULT--------------------
A -> B -> C -> D -> E -> B -> C [Cost: 30]
A -> B -> C -> D -> C [Cost: 25]
A -> B -> C -> E -> B -> C -> E -> B -> C [Cost: 27]
A -> B -> C -> E -> B -> C [Cost: 18]
A -> B -> C [Cost: 9]
A -> E -> B -> C -> D -> C [Cost: 30]
A -> E -> B -> C -> E -> B -> C [Cost: 23]
A -> E -> B -> C [Cost: 14]
A -> D -> E -> B -> C -> E -> B -> C [Cost: 27]
A -> D -> E -> B -> C [Cost: 18]
A -> D -> C -> D -> C [Cost: 29]
A -> D -> C -> E -> B -> C [Cost: 22]
A -> D -> C [Cost: 13]
-----------------------------------------
``