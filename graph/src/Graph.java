
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.Collectors;

public class Graph {

	// Dijkstra's algorithm to find the shortest path from source to target
	public static Map<Integer, Integer> dijkstra(Map<Integer, List<Pair<Integer, Integer>>> graph, int source) {
		// Initialize distances and priority queue
		Map<Integer, Integer> distances = new HashMap<>();
		Map<Integer, Integer> previous = new HashMap<>();
		PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getWeight));

		// Set initial distances to infinity, except for the source
		for (Integer vertex : graph.keySet()) {
			distances.put(vertex, Integer.MAX_VALUE);
			previous.put(vertex, null);
		}
		distances.put(source, 0);
		pq.add(new Pair<>(source, 0));

		while (!pq.isEmpty()) {
			// Get the vertex with the smallest distance
			int u = pq.poll().getNeighbor();

			// Process each neighbor
			for (Pair<Integer, Integer> edge : graph.get(u)) {
				int v = edge.getNeighbor();
				int weight = edge.getWeight();
				int newDist = distances.get(u) + weight;

				// If a shorter path is found
				if (newDist < distances.get(v)) {
					distances.put(v, newDist);
					previous.put(v, u);
					pq.add(new Pair<>(v, newDist));
				}
			}
		}

		return distances;
	}

	// Reconstruct the shortest path from source to target
	public static List<Integer> getShortestPath(Map<Integer, Integer> previous, int target) {
		List<Integer> path = new ArrayList<>();
		for (Integer at = target; at != null; at = previous.get(at)) {
			path.add(at);
		}
		Collections.reverse(path);
		return path;
	}
	
	// Calculate the eccentricity of a vertex
    public static int eccentricity(Map<Integer, List<Pair<Integer, Integer>>> graph, int vertex) {
        Map<Integer, Integer> distances = dijkstra(graph, vertex);
        return distances.values().stream().max(Integer::compareTo).orElse(Integer.MAX_VALUE);
    }

    // Calculate the radius and diameter of the graph
    public static Map<String, Integer> calculateGraphProperties(Map<Integer, List<Pair<Integer, Integer>>> graph) {
        int radius = Integer.MAX_VALUE;
        int diameter = 0;

        for (Integer vertex : graph.keySet()) {
            int eccentricity = eccentricity(graph, vertex);
            radius = Math.min(radius, eccentricity);
            diameter = Math.max(diameter, eccentricity);
        }

        Map<String, Integer> properties = new HashMap<>();
        properties.put("radius", radius);
        properties.put("diameter", diameter);
        return properties;
    }

	public static void main(String[] args) {
		// Reading command line Input.
		Integer numNodes=4;
		Integer numEdges=4;
		if(args.length==2) {
		numNodes=Integer.parseInt(args[0]);
		numEdges=Integer.parseInt(args[1]);
		}
		// Example graph with weights
		
		 Map<Integer, List<Pair<Integer, Integer>>>
		 graph=RandomGraphGenerator.generateRandomWeightedConnectedGraph(numNodes, numEdges, numNodes).getGraph();

		// Randomly select source and target vertices
		Random rand = new Random();
		List<Integer> vertices = new ArrayList<>(graph.keySet());
		int source = vertices.get(rand.nextInt(vertices.size()));
		int target = vertices.get(rand.nextInt(vertices.size()));

		// Ensure source and target are not the same
		while (source == target) {
			target = vertices.get(rand.nextInt(vertices.size()));
		}
		 System.out.println("Graph with weight(weights will get added randomly from 1 to the number of vertices):");
		 System.out.print("{");
	        for (Map.Entry<Integer, List<Pair<Integer, Integer>>> entry : graph.entrySet()) {
	        	if(entry.getKey()==numNodes) {
	            System.out.println(":"+entry.getKey() + " [" + 
	                entry.getValue().stream()
	                    .map(Pair::toString)
	                    .collect(Collectors.joining(", "))+"]}");
	        	}else {
	        		 System.out.println(":"+entry.getKey() + " [" + 
	        	                entry.getValue().stream()
	        	                    .map(Pair::toString)
	        	                    .collect(Collectors.joining(", "))+"],");

	        	}
	        	
	        }

			System.out.println("Source: " + source);
			System.out.println("Target: " + target);

		// Run Dijkstra's algorithm from source
		Map<Integer, Integer> distances = dijkstra(graph, source);
		List<Integer> path = getShortestPath(distances, target);

		// Output results
		System.out.println("Shortest Path: " + path);
		System.out.println("Distance: " + distances.get(target));
		
		
		
		// calculating eccentricity of vertex
		for(Integer vertex:vertices) {
			System.out.println("eccentricity of vertex-> "+vertex+":"+eccentricity(graph, vertex));
		}
		
		// calculating radius and diameter
		Map<String, Integer> gprop=calculateGraphProperties(graph);
		System.out.println(gprop);
	}
	
}
