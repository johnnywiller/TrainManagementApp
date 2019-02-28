package app.graph;


import java.util.*;
import java.lang.*;
import java.io.*;


public class AllPairShortestPath {
    final static int INF = 99999, V = 5;

    void floydWarshall(String graph[][]) {
        String dist[][] = new String[V][V];
        int i, j, k;

        /* Initialize the solution matrix same as input graph matrix.
           Or we can say the initial values of shortest distances
           are based on shortest paths considering no intermediate
           vertex. */

        for (i = 0; i < V; i++)
            for (j = 0; j < V; j++)
                dist[i][j] = graph[i][j];

        /* Add all vertices one by one to the set of intermediate
           vertices.
          ---> Before start of an iteration, we have shortest
               distances between all pairs of vertices such that
               the shortest distances consider only the vertices in
               set {0, 1, 2, .. k-1} as intermediate vertices.
          ----> After the end of an iteration, vertex no. k is added
                to the set of intermediate vertices and the set
                becomes {0, 1, 2, .. k} */

        for (k = 0; k < V; k++) {
            // Pick all vertices as source one by one
            for (i = 0; i < V; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (j = 0; j < V; j++) {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
//
//                    System.out.print("Pivot = " + k + "," + j + " ");
//                    System.out.print("Source = " + i + "," + k + " ");
//                    System.out.print("Destination = " + i + "," + j);
//                    System.out.println();

                    System.out.print("Pivot = " + dist[k][j] + " ");
                    System.out.print("Source = " + dist[i][k] + " ");
                    System.out.print("Destination = " + dist[i][j] + " ");
                    System.out.println();
//                    if (dist[i][k] + dist[k][j] < dist[i][j])
//                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }

        // Print the shortest distance matrix
//        printSolution(dist);
    }

    void printSolution(int dist[][]) {
        System.out.println("The following matrix shows the shortest " +
                "distances between every pair of vertices");

        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (dist[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j] + "   ");
            }
            System.out.println();
        }
    }

    // Driver program to test above function
    public static void main(String[] args) {
        /* Let us create the following weighted graph
           10
        (0)------->(3)
        |         /|\
        5 |          |
        |          | 1
        \|/         |AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
        (1)------->(2)
           3           */
//        int graph[][] = {{0, 5, INF, 5, 7},
//                        {INF, 0, 4, INF, INF},
//                        {INF, INF, 0, 8, 2},
//                        {INF, INF, 8, 0, 6},
//                        {INF, 3, INF, 0, INF}
//        };
//
        String graph[][] = {{"A", "B", "C", "D", "E"},
                {"A", "B", "C", "D", "E"},
                {"A", "B", "C", "D", "E"},
                {"A", "B", "C", "D", "E"},
                {"A", "B", "C", "D", "E"}};

        AllPairShortestPath a = new AllPairShortestPath();

        // Print the solution
        a.floydWarshall(graph);
    }
}


