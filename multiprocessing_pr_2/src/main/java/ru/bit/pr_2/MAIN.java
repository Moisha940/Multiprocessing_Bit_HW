package ru.bit.pr_2;


public class MAIN {
    private static final int GRAPH_SIZE = 500;
    private static final int NUMBER_OF_THREADS = 4;
    private static final int NUMBER_OF_RUNS = 5;
    private static final int SOURCE_NODE = 5;

    private static double SEQ_TIME = 0;
    private static double PAR_TIME = 0;

    public static void main(String[] args) {
        Graph graph = new CubeGraph(GRAPH_SIZE);

        int[] seqDist = seqBfsStart(graph);
        int[] parDist = parBfsStart(graph);

        System.out.println(String.format("Параллельный алгоритм эффективней последовательного в: %.2f", SEQ_TIME / PAR_TIME));

        new Checker(seqDist, parDist).check();
    }

    private static int[] seqBfsStart(Graph graph) {
        System.out.println("Прогон последовательного алгоритма:");

        SeqBFS seqBFS = new SeqBFS(graph, SOURCE_NODE);
        long seqTime = 0;
        int[] seqDist = null;

        for (int i = 0; i < NUMBER_OF_RUNS; i++) {
            long startTime = System.nanoTime();
            seqDist = seqBFS.execute();
            long endTime = System.nanoTime();
            long time = (endTime - startTime) / 1_000_000;
            seqTime += time;
            System.out.println("Последовательный запуск " + (i + 1) + ": " + time + " мс");
        }

        SEQ_TIME = seqTime / (double) NUMBER_OF_RUNS;
        System.out.println("Среднее время последовательного BFS: " + SEQ_TIME + " мс\n");

        return seqDist;
    }

    private static int[] parBfsStart(Graph graph) {
        System.out.println("Прогон параллельного алгоритма:");

        ParBFS parBFS = new ParBFS(graph, SOURCE_NODE, NUMBER_OF_THREADS);
        long parTime = 0;
        int[] parDist = null;

        for (int i = 0; i < NUMBER_OF_RUNS; i++) {
            long startTime = System.nanoTime();
            parDist = parBFS.execute();
            long endTime = System.nanoTime();
            long time = (endTime - startTime) / 1_000_000;
            parTime += time;
            System.out.println("Параллельный запуск " + (i + 1) + ": " + time + " мс");
        }

        PAR_TIME = parTime / (double) NUMBER_OF_RUNS;
        System.out.println("Среднее время параллельного BFS: " + PAR_TIME + " мс\n");

        return parDist;
    }

}
