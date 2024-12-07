package ru.bit.pr_2;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CubeGraph implements Graph {
    private final int N;

    @Override
    public List<Integer> getNeighbors(int node) {
        List<Integer> neighbors = new ArrayList<>(6);

        int x = node / (N * N);
        int y = (node / N) % N;
        int z = node % N;

        addNeighbor(neighbors, x - 1, y, z);
        addNeighbor(neighbors, x + 1, y, z);
        addNeighbor(neighbors, x, y - 1, z);
        addNeighbor(neighbors, x, y + 1, z);
        addNeighbor(neighbors, x, y, z - 1);
        addNeighbor(neighbors, x, y, z + 1);

        return neighbors;
    }

    @Override
    public int getNumberOfNodes() {
        return N * N * N;
    }

    private void addNeighbor(List<Integer> neighbors, int x, int y, int z) {
        if (x >= 0 && x < N && y >= 0 && y < N && z >= 0 && z < N) {
            neighbors.add(x * N * N + y * N + z);
        }
    }
}
