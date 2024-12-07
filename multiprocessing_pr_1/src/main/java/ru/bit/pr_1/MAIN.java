package ru.bit.pr_1;

import java.util.Random;

public class MAIN {
    private static final int SIZE = 100_000_000;
    private static final int NUM_RUNS = 5;

    public static void main(String[] args) {
        long parTime = 0;
        long seqTime = 0;

        System.out.printf("\nРазмера массива: %d ", SIZE);
        System.out.printf("\nКоличество запусков: %d", NUM_RUNS);
        System.out.printf("\nПорог переключения: %d\n\n", TotalTime.THRESHOLD);

        for (int run = 1; run <= NUM_RUNS; ++run) {
            int[] arrayForPar = getRandomArray();
            int[] arrayForSeq = arrayForPar.clone();

            parTime = TotalTime.getParTime(arrayForPar, parTime, run);
            seqTime = TotalTime.getSeqTime(arrayForSeq, seqTime, run);
        }

        System.out.printf("\nСреднее время выполнения последовательной сортировки: %.1f мс", (double) (seqTime / NUM_RUNS));
        System.out.printf("\nСреднее время выполнения параллельной сортировки: %.1f мс", (double) (parTime / NUM_RUNS));
        System.out.printf("\nПараллельная сортировка быстрее последовательной в %.3f раза\n", (double) (seqTime) / (parTime));
    }

    private static int[] getRandomArray() {
        int[] array = new int[SIZE];
        Random random = new Random();

        for (int i = 0; i < SIZE; ++i) {
            array[i] = random.nextInt();
        }

        return array;
    }
}
