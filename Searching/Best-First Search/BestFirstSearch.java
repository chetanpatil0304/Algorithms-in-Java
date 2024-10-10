// Import necessary libraries
import java.util.*;

public class BestFirstSearch {

    public static List<String> bestFirstSearch(Map<String, List<String>> graph, String start, String goal, Map<String, Integer> heuristic) {
        // Priority Queue to store nodes with priority based on heuristic value
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(heuristic::get));
        Set<String> visited = new HashSet<>();
        Map<String, String> cameFrom = new HashMap<>();

        // Enqueue the start node
        priorityQueue.add(start);
        cameFrom.put(start, null);

        while (!priorityQueue.isEmpty()) {
            String current = priorityQueue.poll();

            // If the goal is reached, reconstruct and return the path
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, start, goal);
            }

            visited.add(current);

            // Explore the neighbors
            for (String neighbor : graph.get(current)) {
                if (!visited.contains(neighbor) && !priorityQueue.contains(neighbor)) {
                    priorityQueue.add(neighbor);
                    cameFrom.putIfAbsent(neighbor, current);  // Track the path
                }
            }
        }

        // Return empty list if no path found
        return Collections.emptyList();
    }

    public static List<String> reconstructPath(Map<String, String> cameFrom, String start, String goal) {
        List<String> path = new ArrayList<>();
        String current = goal;
        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        // Example graph as an adjacency list
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("D", "E"));
        graph.put("C", Arrays.asList("F"));
        graph.put("D", Arrays.asList());
        graph.put("E", Arrays.asList("F"));
        graph.put("F", Arrays.asList());

        // Example heuristic values for each node
        Map<String, Integer> heuristic = new HashMap<>();
        heuristic.put("A", 6);
        heuristic.put("B", 4);
        heuristic.put("C", 5);
        heuristic.put("D", 2);
        heuristic.put("E", 1);
        heuristic.put("F", 0);

        // Perform Best-First Search from start 'A' to goal 'F'
        List<String> path = bestFirstSearch(graph, "A", "F", heuristic);
        
        // Output the result
        System.out.println("Path found: " + path);
    }
}
