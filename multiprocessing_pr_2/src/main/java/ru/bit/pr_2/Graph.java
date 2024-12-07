package ru.bit.pr_2;

import java.util.List;

public interface Graph {
    List<Integer> getNeighbors(int node);

    int getNumberOfNodes();
}
