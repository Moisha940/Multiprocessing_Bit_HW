package ru.bit.pr_2;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public abstract class BFS {
    Graph graph;
    int sourceNode;

    public abstract int[] execute();
}

