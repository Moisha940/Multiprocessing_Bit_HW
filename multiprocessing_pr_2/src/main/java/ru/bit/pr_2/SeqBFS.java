package ru.bit.pr_2;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SeqBFS extends BFS {

    public SeqBFS(Graph graph, int sourceNode) {
        super(graph, sourceNode);
    }

    @Override
    public int[] execute() {
        int n = graph.getNumberOfNodesInGraph();
        Queue<Integer> queue = new LinkedList<>();
        int[] distances = new int[n];
        //boolean[] visited = new boolean[n];
        BitSet visited = new BitSet(n);

        queue.add(sourceNode);
        distances[sourceNode] = 0;
        //visited[sourceNode] = true;
        visited.set(sourceNode);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            int currentDistance = distances[current];
            List<Integer> neighbors = graph.getNeighborsForNode(current);
            for (int neighbor : neighbors) {
                //if (!visited[neighbor]) {
                if (!visited.get(neighbor)) {
                    //visited[neighbor] = true;
                    visited.set(neighbor);
                    distances[neighbor] = currentDistance + 1;
                    queue.add(neighbor);
                }
            }
        }
        return distances;
    }
}

