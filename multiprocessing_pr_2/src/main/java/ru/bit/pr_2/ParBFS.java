package ru.bit.pr_2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ParBFS extends BFS {
    private final int numThreads;

    public ParBFS(Graph graph, int sourceNode, int numThreads) {
        super(graph, sourceNode);
        this.numThreads = numThreads;
    }

    @Override
    public int[] execute() {
        int N = graph.getNumberOfNodesInGraph();
        AtomicIntegerArray visited = new AtomicIntegerArray(N);
        AtomicIntegerArray distances = new AtomicIntegerArray(N);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        try {
            List<Integer> currentFront = new ArrayList<>();
            currentFront.add(sourceNode);
            visited.set(sourceNode, 1);
            distances.set(sourceNode, 0);

            while (!currentFront.isEmpty()) {
                List<Future<List<Integer>>> futures = new ArrayList<>();

                int chunkSize = (int) Math.ceil((double) currentFront.size() / numThreads);

                for (int i = 0; i < numThreads; ++i) {
                    int start = i * chunkSize;
                    int end = Math.min(start + chunkSize, currentFront.size());
                    if (start >= end) break;
                    List<Integer> subList = currentFront.subList(start, end);
                    futures.add(executor.submit(new BFSForBatch(subList, visited, distances)));
                }

                List<Integer> nextFront = new ArrayList<>();
                for (Future<List<Integer>> future : futures) {
                    try {
                        nextFront.addAll(future.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                currentFront = nextFront;
            }
        } finally {
            executor.shutdown();
        }

        int[] distancesArray = new int[N];
        for (int i = 0; i < N; ++i) {
            distancesArray[i] = distances.get(i);
        }

        return distancesArray;
    }

    private class BFSForBatch implements Callable<List<Integer>> {
        private final List<Integer> batch;
        private final AtomicIntegerArray visited;
        private final AtomicIntegerArray distances;

        public BFSForBatch(List<Integer> batch, AtomicIntegerArray visited, AtomicIntegerArray distances) {
            this.batch = batch;
            this.visited = visited;
            this.distances = distances;
        }

        @Override
        public List<Integer> call() {
            List<Integer> localNextFront = new ArrayList<>();
            for (int node : batch) {
                int currentDistance = distances.get(node);
                List<Integer> neighbors = graph.getNeighborsForNode(node);
                for (int neighbor : neighbors) {
                    if (visited.compareAndSet(neighbor, 0, 1)) {
                        distances.set(neighbor, currentDistance + 1);
                        localNextFront.add(neighbor);
                    }
                }
            }
            return localNextFront;
        }
    }
}
