

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomGraphGenerator {

	  static class Graph {
	        private int numVertices;
	        public Map<Integer, List<Pair<Integer, Integer>>> adjList;

	       Graph(int numVertices) {
	            this.numVertices = numVertices;
	            adjList = new HashMap<>();
	            for (int i = 1; i <= numVertices; i++) {
	                adjList.put(i, new ArrayList<>());
	            }
	        }

	        void addEdge(int from, int to, int weight) {
	            if (!containsEdge(from, to)) {
	                adjList.get(from).add(new Pair(to, weight));
	            }
	        }

	        boolean containsEdge(Integer from, Integer to) {
	            for (Pair edge : adjList.get(from)) {
	                if (edge.getNeighbor() == to) {
	                    return true;
	                }
	            }
	            return false;
	        }
	        public Map<Integer, List<Pair<Integer, Integer>>> getGraph(){
	        	return adjList;
	        }

	        @Override
	        public String toString() {
	            StringBuilder sb = new StringBuilder();
	            for (int i = 1; i <= numVertices; i++) {
	                sb.append(i).append(": ").append(adjList.get(i)).append("\n");
	            }
	            return sb.toString();
	        }
	    }

	    public static Graph generateRandomWeightedConnectedGraph(int numVertices, int numEdges, int maxWeight) {
	        if (numEdges < numVertices - 1) {
	            throw new IllegalArgumentException("Number of edges must be at least numVertices - 1 to ensure connectivity.");
	        }

	        Graph graph = new Graph(numVertices);
	        Random rand = new Random();

	        // Create a connected graph by forming a simple path
	        for (int i = 1; i <= numVertices; i++) {
	            int weight = rand.nextInt(maxWeight) + 1; // Weights are between 1 and maxWeight
	            graph.addEdge(i, i , weight);
	        }

	        // Generate additional random edges
	        List<int[]> possibleEdges = new ArrayList<>();
	        for (int i = 1; i <= numVertices; i++) {
	            for (int j = 1; j <= numVertices; j++) {
	                if (i != j && !graph.containsEdge(i, j)) {
	                    possibleEdges.add(new int[]{i, j});
	                }
	            }
	        }

	        Collections.shuffle(possibleEdges, rand);

	        int edgesToAdd = numEdges - (numVertices - 1);
	        for (int i = 1; i <= edgesToAdd; i++) {
	            if (possibleEdges.isEmpty()) break;
	            int[] edge = possibleEdges.remove(0);
	            int weight = rand.nextInt(maxWeight) + 1; // Weights are between 1 and maxWeight
	            graph.addEdge(edge[0], edge[1], weight);
	        }

	        return graph;
	    }
}
